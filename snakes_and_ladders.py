import os
import random


def clearScreen():
    # For Windows
    if os.name == "nt":
        _ = os.system("cls")
    # For Unix/Linux/Mac
    else:
        _ = os.system("clear")


d1 = {1: 38, 4: 14, 8: 10, 28: 74, 21: 42, 50: 67, 71: 92, 80: 99}
d2 = {32: 10, 36: 6, 62: 18, 88: 24, 95: 56, 97: 78}
n = int(input("Enter number of players:"))
l1 = []


def grid(l1, l2):
    for i in range(10, 0, -1):
        k = i * 10
        if i % 2 == 0:
            for j in range(k, k - 10, -1):
                print("%3d" % j, end="")
                for i in range(len(l2)):
                    if l2[i] == j:
                        print(l1[i], end=" ")
                    else:
                        print("  ", end="")
            print()
        else:
            for j in range(k - 9, k + 1):
                print("%3d" % j, end="")
                for i in range(len(l2)):
                    if l2[i] == j:
                        print(l1[i], end=" ")
                    else:
                        print("  ", end="")
            print()


for x in range(1, n + 1):
    print("Enter player", x, "'s symbol:")
    l1.append(input())
l2 = [0 for x in range(1, n + 1)]
j = 0
i = 1
while True:
    print("ladders are", d1)
    print("snakes are", d2)
    print("It's player", j + 1, "turn")
    num = random.randint(1, 6)
    print("you rolled a ", num)
    if l2[j] + num > 100:
        "lol"
    else:
        l2[j] = l2[j] + num
    if l2[j] in d1.keys():
        print("Its a ladder!")
        l2[j] = d1[l2[j]]
    elif l2[j] in d2.keys():
        print("Oh no! Its a Snake")
        l2[j] = d2[l2[j]]
    print("Current score is", l2[j])
    grid(l1, l2)
    asf = input()
    clearScreen()
    if l2[j] == 100:
        print("player", j + 1, "won")
        break
    if j + 1 == n:
        j = 0
    else:
        j = j + 1