import tensorflow as tf
import matplotlib.pyplot as plt

mnist = tf.keras.datasets.mnist  # Загрузка датасета MNIST, содержащего изображения рукописных цифр

# Разделение датасета на обучающую и тестовую выборки
(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

# Нормализация значений пикселей изображений к диапазону [0, 1]
train_images = train_images.astype('float32') / 255
test_images = test_images.astype('float32') / 255

# Создание модели нейронной сети как последовательности слоев
network = tf.keras.models.Sequential()

# Добавление слоя Flatten для преобразования двумерного массива пикселей в одномерный
network.add(tf.keras.layers.Flatten(input_shape=(28, 28)))

# Добавление полносвязного слоя с 512 нейронами и функцией активации ReLU
network.add(tf.keras.layers.Dense(512, activation='relu'))

# Добавление выходного слоя с 10 нейронами (по числу классов) и функцией активации softmax
network.add(tf.keras.layers.Dense(10, activation='softmax'))

# Компиляция модели с оптимизатором Adam, функцией потерь sparse_categorical_crossentropy и метрикой accuracy
network.compile(optimizer='adam',
                loss='sparse_categorical_crossentropy',
                metrics=['accuracy'])

# Обучение модели на обучающей выборке в течение 10 эпох с размером пакета 128
# и валидационной выборкой для оценки производительности модели на данных, которые она не видела в процессе обучения
history = network.fit(train_images, train_labels, epochs=10, batch_size=128, validation_data=(test_images, test_labels))

# Получение значений функции потерь на валидационной выборке на каждой эпохе
val_loss_values = history.history['val_loss']
epochs = range(1, len(val_loss_values) + 1)

# Визуализация кривой потерь при валидации в зависимости от числа эпох
plt.plot(epochs, val_loss_values, 'b', label='Потери при валидации')
plt.title('Потери при валидации')
plt.xlabel('Эпохи')
plt.ylabel('Потери')
plt.legend()
plt.show()
