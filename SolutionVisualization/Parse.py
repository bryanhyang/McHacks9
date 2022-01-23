from datetime import datetime, timedelta
from turtle import update

import numpy as np
import pandas as pd
from matplotlib import pyplot
import matplotlib.cm as cm

def plot_passengers_pulling_up_to_station(passengers):
    pyplot.plot(times, passengers["A"], label="Station A")
    pyplot.plot(times, passengers["B"], label="Station B")
    pyplot.plot(times, passengers["C"], label="Station C")
    pyplot.legend()
    pyplot.xlabel("Time (24 hour format)")
    pyplot.ylabel("Passengers arriving at station")
    pyplot.show()

def parse_station_data(station_data, passengers_arriving):
    # rename arrival time to time
    passengers_leaving_A = station_data[["A_ArrivalTime", "A_Boarding"]].rename(columns={"A_ArrivalTime":"Time"})
    passengers_leaving_B = station_data[["B_ArrivalTime", "B_Boarding"]].rename(columns={"B_ArrivalTime":"Time"})
    passengers_leaving_C = station_data[["C_ArrivalTime", "C_Boarding"]].rename(columns={"C_ArrivalTime":"Time"})

    # merge arriving and boarding dataframes
    timeline = pd.concat([passengers_arriving, passengers_leaving_A, passengers_leaving_B, passengers_leaving_C])
    # sort by time, set time to index
    timeline["Time"] = pd.to_datetime(timeline["Time"], format="%H:%M").dt.strftime("%H:%M")
    timeline = timeline.set_index("Time").sort_index()
    # drop any nan rows, convert to 0
    timeline = timeline.fillna(0)
    # combine rows with same time
    timeline = timeline.groupby(["Time"])[timeline.columns].agg(lambda x: sum(x)).astype("int64").reset_index()
    timeline = timeline.join(pd.DataFrame({"Leftover A": timeline["A"] - timeline["A_Boarding"]}))
    timeline = timeline.join(pd.DataFrame({"Leftover B": timeline["B"] - timeline["B_Boarding"]}))
    timeline = timeline.join(pd.DataFrame({"Leftover C": timeline["C"] - timeline["C_Boarding"]}))

    for station in ["A", "B", "C"]:
        total = 0
        total_passengers = []
        for change in timeline[f"Leftover {station}"]:    
            total += change
            total_passengers.append(total)
        timeline = timeline.join(pd.DataFrame({f"Total Passengers {station}": total_passengers}))

    return timeline

def plot_stations(timeline):
    pyplot.plot(timeline["Time"], timeline["Total Passengers A"], label="Station A")
    pyplot.plot(timeline["Time"], timeline["Total Passengers B"], label="Station B")
    pyplot.plot(timeline["Time"], timeline["Total Passengers C"], label="Station C")
    pyplot.legend()
    pyplot.xlabel("Time (24 hour format)")
    pyplot.xticks(rotation=45)

    pyplot.ylabel("Passengers waiting at station")

def plot_trains(station_data):
    # calculate remaining capacitry on each train after arriving at each station
    remaining_cap = pd.DataFrame({"A_remaining": station_data["A_AvailCap"] - station_data["A_Boarding"],
                                  "B_remaining": station_data["B_AvailCap"] - station_data["B_Boarding"],
                                  "C_remaining": station_data["C_AvailCap"] - station_data["C_Boarding"],
                                })
    train_timeline = station_data[["TrainNum", "A_ArrivalTime", "B_ArrivalTime", "C_ArrivalTime"]].join(remaining_cap)

    # fixing time format 7:00 --> 07:00
    train_timeline["A_ArrivalTime"] = pd.to_datetime(train_timeline["A_ArrivalTime"], format="%H:%M").dt.strftime("%H:%M")
    train_timeline["B_ArrivalTime"] = pd.to_datetime(train_timeline["B_ArrivalTime"], format="%H:%M").dt.strftime("%H:%M")
    train_timeline["C_ArrivalTime"] = pd.to_datetime(train_timeline["C_ArrivalTime"], format="%H:%M").dt.strftime("%H:%M")

    # plot each trains remaining capacity at each station
    colors = cm.rainbow(np.linspace(0, 1, len(train_timeline["TrainNum"])))
    for train, color in zip(train_timeline["TrainNum"], colors):
        x_vals = [train_timeline.iloc[train-1][f"{station}_ArrivalTime"] for station in ["A", "B", "C"]]
        y_vals = [train_timeline.iloc[train-1][f"{station}_remaining"] for station in ["A", "B", "C"]]
        pyplot.plot(x_vals,
                    y_vals,
                    color=color,
                    linestyle="--")
        for x, y in zip(x_vals, y_vals):
            pyplot.annotate(xy=(x, y), text=train)

def plot_all(timeline, station_data):
    plot_stations(timeline)
    plot_trains(station_data)
    pyplot.show()

def main():
    # kms
    #station_data = pd.read_csv(r"..\TrainSolution\assets\in\refSchedule.csv")
    station_data = pd.read_csv(r"..\TrainSolution\assets\out\newSchedule1.csv")
    passengers_arriving = pd.read_csv(r"..\TrainSolution\assets\in\passengers.csv", names = ("Time", "A", "B", "C"))
    # plot_passengers_pulling_up_to_station(passengers_arriving)

    timeline = parse_station_data(station_data, passengers_arriving)
    plot_all(timeline, station_data)

if __name__ == "__main__":
    main()
