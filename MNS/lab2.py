import numpy as np


# Функция активации - сигмоида
def sigmoid(x):
    return 1 / (1 + np.exp(-x))


# Производная функции активации сигмоиды
def sigmoid_derivative(x):
    return x * (1 - x)


# Функция потерь - среднеквадратичная ошибка
def mse_loss(y_true, y_pred):
    return np.mean((y_true - y_pred) ** 2)

# Обучающие данные
X = np.array([[8, 8],
              [1, 6],
              [11, 5]])
y = np.array([[95],
              [43],
              [52]])

# Нормализация данных
X_normalized = X / np.amax(X, axis=0)
y_normalized = y / 100

# Инициализация весов сети
input_size = 2
hidden_size1 = 3
hidden_size2 = 3
output_size = 1

np.random.seed(1)
weights_input_hidden1 = np.random.uniform(
    size=(input_size, hidden_size1))  # Веса между входным слоем и первым скрытым слоем
weights_hidden1_hidden2 = np.random.uniform(
    size=(hidden_size1, hidden_size2))  # Веса между первым и вторым скрытыми слоями
weights_hidden2_output = np.random.uniform(
    size=(hidden_size2, output_size))  # Веса между вторым скрытым слоем и выходным слоем

# Обучение нейронной сети
learning_rate = 0.1  # Скорость обучения
epochs = 1000

for epoch in range(epochs):
    # Прямой проход
    hidden1_input = np.dot(X_normalized,
                           weights_input_hidden1)  # Вычисление входного сигнала для первого скрытого слоя
    hidden1_output = sigmoid(hidden1_input)  # Вычисление выходного сигнала для первого скрытого слоя

    hidden2_input = np.dot(hidden1_output,
                           weights_hidden1_hidden2)  # Вычисление входного сигнала для второго скрытого слоя
    hidden2_output = sigmoid(hidden2_input)  # Вычисление выходного сигнала для второго скрытого слоя

    output_input = np.dot(hidden2_output, weights_hidden2_output)  # Входной сигнал для выходного слоя
    output = sigmoid(output_input)

    # Вычисление ошибки
    loss = mse_loss(y_normalized, output)

    # Обратный проход
    output_error = y_normalized - output
    output_delta = output_error * sigmoid_derivative(output)

    hidden2_error = output_delta.dot(weights_hidden2_output.T)
    hidden2_delta = hidden2_error * sigmoid_derivative(hidden2_output)

    hidden1_error = hidden2_delta.dot(weights_hidden1_hidden2.T)
    hidden1_delta = hidden1_error * sigmoid_derivative(hidden1_output)

    # Обновление весов
    weights_hidden2_output += hidden2_output.T.dot(output_delta) * learning_rate
    weights_hidden1_hidden2 += hidden1_output.T.dot(hidden2_delta) * learning_rate
    weights_input_hidden1 += X_normalized.T.dot(hidden1_delta) * learning_rate

    if epoch % 100 == 0:
        print(f'Эпоха {epoch}: ошибка = {loss}')


# Прогнозирование
def predict(X):
    hidden1_output = sigmoid(np.dot(X / np.amax(X, axis=0), weights_input_hidden1))
    hidden2_output = sigmoid(np.dot(hidden1_output, weights_hidden1_hidden2))
    output = sigmoid(np.dot(hidden2_output, weights_hidden2_output))
    return output * 100


# Пример прогнозирования
prediction = predict(np.array([[4, 7]]))
print(f'Оценка за экзамен: {prediction[0][0]}')

