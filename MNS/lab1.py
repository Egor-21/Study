import numpy as np

class Neuron:
    def __init__(self, num_inputs, activation_function):
        self.num_inputs = num_inputs
        self.weights = np.random.rand(num_inputs)  # Инициализация случайными весами
        self.bias = np.random.rand()  # Инициализация случайного смещения
        self.activation_function = activation_function

    def activate(self, inputs):
        # Вычисление взвешенной суммы входов и применение функции активации
        z = np.dot(self.weights, inputs) + self.bias
        return self.activation_function(z)

# Пример использования класса нейрона
def sigmoid(x):
    return 1 / (1 + np.exp(-x))

# Создание нейрона с переменным числом входов
neuron = Neuron(num_inputs=3, activation_function=sigmoid)

inputs = np.array([0.5, 0.3, 0.2])

# Вычисление выхода нейрона
output = neuron.activate(inputs)
print("Выход:", output)
