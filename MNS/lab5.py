import tensorflow as tf
from tensorflow.keras import layers, models
from tensorflow.keras.datasets import cifar10
import matplotlib.pyplot as plt
import numpy as np
import random


# Загрузка и предобработка датасета CIFAR-10
(x_train, y_train), (x_test, y_test) = cifar10.load_data()
x_train, x_test = x_train / 255.0, x_test / 255.0

# Определение архитектуры модели
model = models.Sequential([
    layers.Conv2D(32, (3, 3), activation='relu', input_shape=(32, 32, 3)),
    layers.MaxPooling2D((2, 2)),
    layers.Conv2D(64, (3, 3), activation='relu'),
    layers.MaxPooling2D((2, 2)),
    layers.Conv2D(64, (3, 3), activation='relu'),
    layers.Flatten(),
    layers.Dense(64, activation='relu'),
    layers.Dense(10)  # 10 классов в CIFAR-10
])

# Компиляция модели
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.001),
              loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
              metrics=['accuracy'])

# Обучение модели с сохранением истории потерь
history = model.fit(x_train, y_train, epochs=5, validation_data=(x_test, y_test))

# График потерь
plt.plot(history.history['loss'], label='Training Loss')
plt.plot(history.history['val_loss'], label='Validation Loss')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.legend()
plt.show()

# Функция для демонстрации работы модели
def predict_image(model, image):
    # Преобразование изображения к нужному формату
    image = np.expand_dims(image, axis=0)
    # Предсказание класса
    prediction = model.predict(image)
    # Получение метки класса
    predicted_class = np.argmax(prediction)
    return predicted_class

# Выбор и демонстрация работы модели на 5 случайных изображениях
for _ in range(5):
    image_index = random.randint(0, len(x_test) - 1)
    test_image = x_test[image_index]
    true_label = y_test[image_index][0]

    predicted_label = predict_image(model, test_image)

    class_names = ['Самолет', 'Автомобиль', 'Птица', 'Кошка', 'Олень', 'Собака', 'Лягушка', 'Лошадь', 'Корабль', 'Грузовик']
    print("Предсказанный класс:", class_names[predicted_label])
    print("Фактический класс:", class_names[true_label])

    plt.imshow(test_image)
    plt.axis('off')
    plt.show()
