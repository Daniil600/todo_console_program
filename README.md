# Это консольная программа TODO

<h2>Для корректной работы</h2>
Этот раздел необходимо выполнить, если у вас возникла такая проблема:
<br></br>

![Image alt](https://github.com/Daniil600/todo_program/blob/master/picture/img.png)

</br>
<b>Для её решения вам необходимо изменить настройки в idea</b>
</br>
Вот полный путь: File -> Settings -> Editor -> File Encoding -> Global Encoting и Project encoding меняем на Default.
После этого перезагрузите Idea.
</br>
У вас при запуске TODO консоль Idea должна выглядить так:
<br></br>

![Image alt](https://github.com/Daniil600/todo_program/blob/master/picture/img_1.png)

<h2>О проекте</h2>
<h3>Цель:</h3>
Этот проект выполняет роль TODO list с сохранением всех задача в xml.
Я старался сделать проект гибким под другие форматы, такие как JSON или YAML.
</br>
</br>
<b>ВАЖНО</b> Когда вы добавите задачи или измените их, 
обязательно завершите программу командой <i>"-stop"</i>,
для того чтобы изменения сохранились в xml

<h3>Как работает:</h3>
В начале у вас выйде окно с таким текстом:
<i>
</br>
Привет, это Консольный TODO. И вот все задачи которые есть:
</br>
1 | Test | Task | 10 | 2000-02-02 | DONE | 2023-08-28
</br>
Вот консольные команды которые доступны:
</br>
Вывод справки по командам: help
</br>
Вывод всех задач: list
</br>
Вывод новых задач: list -s new
</br>
Вывод задач в работе: list -s in progress
</br>
Вывод выполненных задач: list -s done
</br>
Останавливает работу программы: -stop
</br>
Добавление новой задачи: -add
</br>
Изменение задачи: -edit
</br>
удаление задачи: -delete
</br>
Если Вы захотите выйти из команды которую выбрали, просто введите "-stop"
</br>
Введите команду: 
</i>
</br>
</br>
Давайте рассмотрим каждую из команд:

</br>
</br>
<b>list</b> - <i>эта команда выводит все задачи которые есть сейчас.</i>
</br>
</br>
<b>list -s new</b> - <i>эта команда выводит все задачи которые есть сейчас со статусом "Новый"</i>
</br>
</br>
<b>list -s in progress</b> - <i>эта команда выводит все задачи которые есть сейчас со статусом "В процессе"</i>
</br>
</br>
<b>list -s done</b> - <i>эта команда выводит все задачи которые есть сейчас со статусом "Завершеный"</i>
</br>
</br>
<b>-stop</b> - <i>эта команда останавливает программу и сохраняет задачу в xml</i>
</br>
<b>ПРИ ЗАВЕРШЕНИИ ОБЯЗАТЕЛЬНО ВВДИТЕ ЭТУ КОМАНДУ ДЛЯ СОХРАНЕНИЯ ВСЕХ ДАННЫХ</b>
</br>
</br>
<b>-add</b> - <i>эта команда добавляет задачу в List</i>
</br>
</br>
После её ввода Вам будет предложено ввести "Заголовок", "Описание", "Приоритет", "Дату", "Статус".
После этого вы увидите что Ваша задача появилась в "Задачах", проверить это можно введя "list"
</br>
</br>
<b>-edit</b> - эта команда изменяет задачу в List
</br>
</br>
После её ввода Вам будет предложено выбрать задачу для изменения, после вам будет предложенно выбрать поля, которое Вы хотите изменить, и вам будет предложенно ввести новое значение.
После этого вы увидите что Ваша задача изменилась в "Задачах", проверить это можно введя "list"
</br>
</br>
<b>delete</b> - эта команда добавляет задачу по id. Вам предложат на выбор удалить задачу и нужно будет ввести её Id. после чего она будет удалена.
</br>
</br>

<h3>Архитектура:</h3>
<h4>Парсинг</h4>
Для парсинга был выбран parsing DOM. 
Для парсига есть отдельный package с названием <b>"parser"</b>.
<h4>Логика</h4>
Логика приложения написана в "<b>"src/service"</b> где есть пакеты с соответвующими названиями выполнения

<h4>Создание задача</h4>
Для создание задач есть отдельный package с названием <b>"service/create"</b>, где находится класс
с необходимыми методами для создания задач пользователем.
<\br>
<h4>Редактирование задача</h4>
Для редактирования задач есть отдельный package с названием <b>"service/edit"</b>, где находится класс
с необходимыми методами для изменения задач пользователем.
<\br>
<h4>Проверка ввода данных</h4>
Для проверки введенных данных пользователем есть отдельный package с названием <b>"service/check"</b>, где находится класс
с необходимыми методами для проверки.

