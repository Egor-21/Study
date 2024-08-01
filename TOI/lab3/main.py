import random


class AntColony:
    def __init__(self, distances, n_ants=10, n_iterations=100, decay=0.6, alpha=1, beta=2):
        """
        Инициализация муравьиной колонии.

        distances: матрица расстояний между городами.
        n_ants: количество муравьев в колонии.
        n_iterations: количество итераций (циклов), в течение которых муравьи ищут путь.
        decay: коэффициент испарения феромона.
        alpha: параметр, влияющий на важность феромона.
        beta: параметр, влияющий на важность расстояния.
        """
        self.distances = distances
        self.n_ants = n_ants
        self.n_iterations = n_iterations
        self.decay = decay
        self.alpha = alpha
        self.beta = beta
        self.n_cities = len(distances)
        # Инициализация феромонов на ребрах графа.
        self.pheromone = [[1 / (self.n_cities * self.n_cities) for _ in range(self.n_cities)] for _ in
                          range(self.n_cities)]

    def run(self):
        """
        Запуск муравьиного алгоритма для поиска кратчайшего пути.
        """
        shortest_distance = float('inf')
        shortest_path = []
        for _ in range(self.n_iterations):
            # Генерация путей для каждого муравья.
            paths = self.generate_paths()
            # Обновление феромонов на ребрах графа.
            self.update_pheromone(paths)
            # Обновление кратчайшего пути.
            shortest_path, shortest_distance = self.update_shortest(paths, shortest_path, shortest_distance)
        return shortest_path, shortest_distance

    def generate_paths(self):
        """
        Генерация путей для каждого муравья.
        """
        paths = []
        for _ in range(self.n_ants):
            path = self.generate_path()
            paths.append((path, self.calculate_distance(path)))
        return paths

    def generate_path(self):
        """
        Генерация пути для одного муравья.
        """
        path = [random.randint(0, self.n_cities - 1)]
        while len(path) < self.n_cities:
            # Выбор следующего города для посещения.
            next_city = self.choose_next(path)
            path.append(next_city)
        return path

    def choose_next(self, path):
        """
        Выбор следующего города для посещения муравьем.
        """
        last_city = path[-1]
        pheromone_values = self.pheromone[last_city]
        probabilities = [0] * self.n_cities
        total = 0
        for i, value in enumerate(pheromone_values):
            if i not in path:
                # Вычисление вероятности перехода в следующий город.
                probabilities[i] = value ** self.alpha
                total += probabilities[i] ** self.beta
        probabilities = [p / total for p in probabilities]
        # Случайный выбор следующего города на основе вероятностей.
        next_city = random.choices(range(self.n_cities), weights=probabilities)[0]
        return next_city

    def calculate_distance(self, path):
        """
        Вычисление длины пути.
        """
        distance = 0
        for i in range(len(path) - 1):
            distance += self.distances[path[i]][path[i + 1]]
        distance += self.distances[path[-1]][path[0]]
        return distance

    def update_pheromone(self, paths):
        """
        Обновление феромонов на ребрах графа.
        """
        for i in range(self.n_cities):
            for j in range(self.n_cities):
                # Испарение феромона на ребрах.
                self.pheromone[i][j] *= self.decay
        for path, distance in paths:
            for i in range(len(path) - 1):
                # Обновление феромонов на пути муравья.
                self.pheromone[path[i]][path[i + 1]] += 1 / distance
            self.pheromone[path[-1]][path[0]] += 1 / distance

    def update_shortest(self, paths, shortest_path, shortest_distance):
        """
        Обновление кратчайшего пути.
        """
        for path, distance in paths:
            if distance < shortest_distance:
                shortest_distance = distance
                shortest_path = path
        return shortest_path, shortest_distance


# Пример использования
distances = [
    [0, 2, 3, 1],
    [2, 0, 5, 2],
    [8, 4, 0, 1],
    [4, 3, 9, 0]
]
print("Исходный граф: ")
for row in distances:
    print(row)

colony = AntColony(distances)
shortest_path, shortest_distance = colony.run()
print("Кратчайший путь:", shortest_path)
print("Кратчайшая дистанция:", shortest_distance)