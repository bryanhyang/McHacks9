import itertools

import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation

import Parse

timeline = Parse.main()

w = timeline.shape[0]
l = timeline.shape[1]

fig = plt.figure()
ax = plt.axes(xlim=(0, 20), ylim=(0, 200))

line, = ax.plot([], [])

def init():
    line.set_data([], [])
    return line,

def animate(i):
    print(i)
    x = np.linspace(i, i+1, 1000)
    y = np.linspace(timeline["A"][i], timeline["A"][i+1], 1000)
    line.set_data(x, y)
    return line,

anim = animation.FuncAnimation(fig, animate, init_func=init,
                               frames=w, interval=1000)

plt.show()