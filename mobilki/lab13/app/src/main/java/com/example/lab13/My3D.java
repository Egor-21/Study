package com.example.lab13;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class My3D {
    private final int mProgram;
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
            // основание
            0.0f, 0.577f, 0.0f,  // верхний центр
            -0.5f, -0.289f, 0.0f,  // левый нижний угол
            0.5f, -0.289f, 0.0f,  // правый нижний угол
            // боковые грани
            -0.5f, -0.289f, 0.0f,  // левый нижний угол
            0.0f, 0.577f, 0.0f,  // верхний центр
            0.0f, 0.0f, -0.5f,  // верхний ближний угол
            // боковые грани
            0.0f, 0.577f, 0.0f,  // верхний центр
            0.5f, -0.289f, 0.0f,  // правый нижний угол
            0.0f, 0.0f, -0.5f,  // верхний ближний угол
            // боковые грани
            0.5f, -0.289f, 0.0f,  // правый нижний угол
            -0.5f, -0.289f, 0.0f,  // левый нижний угол
            0.0f, 0.0f, -0.5f  // верхний ближний угол
    };
    private short drawOrder[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 }; // порядок рисования вершин
    float color[] = { 0.0f, 0.7f, 0.5f, 1.0f };

    public My3D() {
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // создание пустой OpenGL ES программы
        mProgram = GLES20.glCreateProgram();

        // Добавление вершинного шейдера в программу
        GLES20.glAttachShader(mProgram, vertexShader);

        // Добавление фрагментного шейдера в программу
        GLES20.glAttachShader(mProgram, fragmentShader);

        // Связывание программы
        GLES20.glLinkProgram(mProgram);
    }

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 байта на вершину

    public void draw(float[] mvpMatrix) {
        // Добавляем программу в среду OpenGL ES
        GLES20.glUseProgram(mProgram);

        // Получаем элемент vPosition вершинного шейдера
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Подключаем массив вершин
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Подготовка координат вершин
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // Получаем элемент vColor фрагментного шейдера
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Устанавливаем цвет для рисования
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Получаем матрицу преобразований
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        // Передаём матрицу преобразований проекции и вида камеры в шейдер
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

        // Рисуем тетраэдр
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Отключаем массив вершин
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}