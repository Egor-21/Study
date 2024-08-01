import math
import random


def find_distance(tour, dist):  # Метод, считающий общее расстояние между городами

    total_dist = 0
    for i in range(len(tour)):
        total_dist += dist[tour[i]][tour[(i+1) % len(tour)]]
    return total_dist


def method_annealin(dist, Temp=10000, alpha=0.99, stop_temp=0.001, stop_iter=1000):
    # Находим начальное случайное решение
    curr_solution = random.sample(range(len(dist)), len(dist))
    best_solution = curr_solution
    iters = 0

    while Temp > stop_temp and iters < stop_iter:
        for i in range(100):
            new_solution = curr_solution[:]
            random_item1 = random.randint(0, len(dist) - 1)
            random_item2 = random.randint(0, len(dist) - 1)
            # Меняем случайные элементы пути местами
            new_solution[random_item1], new_solution[random_item2] = new_solution[random_item2], new_solution[random_item1]

            # Считаем разницу длины маршрута между текщим и найденным путями
            delta = find_distance(new_solution, dist) - find_distance(curr_solution, dist)
            # Случай, когда выбираем новый маршрут
            if delta < 0 or random.uniform(0, 1) < math.exp(-delta/Temp):
                curr_solution = new_solution
                # Проверяем является ли текущий маршрут лучше, чем текущий лучший маршрут
                if find_distance(curr_solution, dist) < find_distance(best_solution, dist):
                    best_solution = curr_solution
        # Понижаем температуру
        Temp = Temp * alpha
        iters += 1
    return best_solution, find_distance(best_solution, dist)


# Пример использования
# Создаем матрицу расстояний между городами
dist = [
    [0, 2, 3, 5, 6],
    [2, 0, 4, 2, 3],
    [3, 4, 0, 3, 2],
    [5, 2, 3, 0, 4],
    [6, 3, 2, 4, 0]
]
best_solution, min_distance = method_annealin(dist)
print("Лучший маршрут:", best_solution)
print("Примерное минимальное расстояние:", min_distance)



