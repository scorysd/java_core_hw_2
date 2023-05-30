package ru.geekbrains.lesson2;

import java.util.Random;
import java.util.Scanner;

public class Program {


    private static final  int WIN_COUNT = 3;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '•';

    private static final Scanner SCANNER = new Scanner(System.in);

    private static char[][] field; // Двумерный массив хранит текущее состояние игрового поля

    private static final Random random = new Random();

    private static int fieldSizeX; // Размерность игрового поля
    private static int fieldSizeY; // Размерность игрового поля


    public static void main(String[] args) {
        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (gameCheck(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (gameCheck(DOT_AI, "Компьютер победил!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да)");
            if (!SCANNER.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля
     */
    private static void initialize(){
        // Установим размерность игрового поля
        fieldSizeX = 6;
        fieldSizeY = 6;


        field = new char[fieldSizeX][fieldSizeY];
        // Пройдем по всем элементам массива
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                // Проинициализируем все элементы массива DOT_EMPTY (признак пустого поля)
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля
     * //DO: Поправить отрисовку игрового поля
     */
    private static void printField(){
        System.out.print("+");
        for (int i = 0; i < fieldSizeY * 2 + 1; i++){
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int i = 0; i < fieldSizeX; i++){
            System.out.print(i + 1 + "|");

            for (int j = 0; j <  fieldSizeY; j++)
                System.out.print(field[i][j] + "|");

            System.out.println();
        }

        for (int i = 0; i < fieldSizeY * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();

    }

    /**
     * Обработка хода игрока (человек)
     */
    private static void humanTurn(){
        int x, y;
        int max;
        if (fieldSizeX > fieldSizeY) max = fieldSizeX;
        else max = fieldSizeY;
        do

        {
            System.out.printf("Введите координаты хода X и Y (от 1 до %d) через пробел >>> ", max);
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, ячейка является пустой
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности ввода
     * (координаты хода не должны превышать размерность массива, игрового поля)
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 &&  x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход компьютера
     */
    private static void aiTurn(){
        int x, y;
        do
        {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка победы
     * DO: Переработать метод в домашнем задании
     * @param c
     * @return
     */
    static boolean checkWin(char c) {
        // Проверка по всем горизонталям
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isCellValid(x, y + z) && !isCellEmpty(x, y + z)) {
                        if (field[x][y + z] == field[x][y]) {
                            winpoint++;
                        }
                        else break;
                    }
                    else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        // Проверка по всем вертикалям
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isCellValid(x+z, y) && !isCellEmpty(x+z, y)) {
                        if (field[x+z][y] == field[x][y]) {
                            winpoint++;
                        }
                        else break;
                    }
                    else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        // Проверка по диагоналям вверх
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isCellValid(x-z, y+z) && !isCellEmpty(x-z, y+z)) {
                        if (field[x-z][y+z] == field[x][y]) {
                            winpoint++;
                        }
                        else break;
                    }
                    else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        // Проверка по диагоналям вниз
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                int winpoint = 0;
                for (int z = 0; z < WIN_COUNT; z++) {
                    if (isCellValid(x+z, y+z) && !isCellEmpty(x+z, y+z)) {
                        if (field[x+z][y+z] == field[x][y]) {
                            winpoint++;
                        }
                        else break;
                    }
                    else break;
                }
                if (winpoint == WIN_COUNT) return true;
            }
        }
        return false;
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++)
                if (isCellEmpty(x, y)) return false;
        }
        return true;
    }

    /**
     * Метод проверки состояния игры
     * @param c
     * @param str
     * @return
     */
    static boolean gameCheck(char c, String str){
        if (checkWin(c)){
            System.out.println(str);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }

        return false; // Игра продолжается
    }
        static boolean brain(){
            for (int x = 0; x < fieldSizeX; x++) {
                for (int y = 0; y < fieldSizeY; y++) {
                    int winpoint = 0;
                    for (int z = 0; z < WIN_COUNT; z++) {
                        if (isCellValid(x, y + z) && !isCellEmpty(x, y + z)) {
                            if (field[x][y + z] == field[x][y]) {
                                winpoint++;
                            }
                            else break;
                        }
                        else break;
                    }
                    if (winpoint == WIN_COUNT - 1) return true;
                }
            }
            return false;
        }
}
