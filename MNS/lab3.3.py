import tensorflow as tf  # Импорт библиотеки TensorFlow для работы с нейронными сетями
import matplotlib.pyplot as plt  # Импорт библиотеки Matplotlib для визуализации данных

mnist = tf.keras.datasets.mnist  # Загрузка датасета MNIST, содержащего изображения рукописных цифр

(train_images, train_labels), _ = mnist.load_data()  # Загрузка обучающих изображений и их меток

train_images = train_images.astype('float32') / 255  # Нормализация значений пикселей изображений к диапазону [0, 1]

# Создание модели нейронной сети для задачи классификации
# с использованием среднеквадратичной ошибки в качестве функции потерь
network_mse = tf.keras.models.Sequential()
network_mse.add(tf.keras.layers.Flatten(input_shape=(28, 28)))  # Добавление слоя Flatten для преобразования вектора
network_mse.add(tf.keras.layers.Dense(512, activation='relu'))  # Добавление скрытого полносвязного слоя
network_mse.add(tf.keras.layers.Dense(10, activation='softmax'))  # Добавление выходного слоя
network_mse.compile(optimizer='adam',
                    loss='mean_squared_error',  # Использование среднеквадратичной ошибки как функции потерь
                    metrics=['accuracy'])  # Метрика - точность классификации

# Обучение модели с использованием среднеквадратичной ошибки
history_mse = network_mse.fit(train_images, train_labels, epochs=10, batch_size=128)

# Получение значений функции потерь для графика
loss_values_mse = history_mse.history['loss']
epochs = range(1, len(loss_values_mse) + 1)

# Построение графика потерь при использовании среднеквадратичной ошибки
plt.plot(epochs, loss_values_mse, 'b', label='MSE Loss')
plt.title('Training Loss (MSE)')
plt.xlabel('Epochs')
plt.ylabel('Loss')
plt.legend()
plt.show()

# Создание модели нейронной сети для задачи классификации
# с использованием средней абсолютной ошибки в качестве функции потерь
network_mae = tf.keras.models.Sequential()
network_mae.add(tf.keras.layers.Flatten(input_shape=(28, 28)))  # Добавление слоя Flatten для преобразования вектора
network_mae.add(tf.keras.layers.Dense(512, activation='relu'))  # Добавление скрытого полносвязного слоя
network_mae.add(tf.keras.layers.Dense(10, activation='softmax'))  # Добавление выходного слоя
network_mae.compile(optimizer='adam',
                    loss='mean_absolute_error',  # Использование средней абсолютной ошибки как функции потерь
                    metrics=['accuracy'])  # Метрика - точность классификации

# Обучение модели с использованием средней абсолютной ошибки
history_mae = network_mae.fit(train_images, train_labels, epochs=10, batch_size=128)

# Получение значений функции потерь для графика
loss_values_mae = history_mae.history['loss']
epochs = range(1, len(loss_values_mae) + 1)

# Построение графика потерь при использовании средней абсолютной ошибки
plt.plot(epochs, loss_values_mae, 'r', label='MAE Loss')
plt.title('Training Loss (MAE)')
plt.xlabel('Epochs')
plt.ylabel('Loss')
plt.legend()
plt.show()
