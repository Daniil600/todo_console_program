# Это консольная программа TODO

<h2>Для корректной работы</h2>
Этот раздел необходимо выполнить, если у вас возникла такая проблема:
<br></br>

![Image alt](https://github.com/Daniil600/todo_program/blob/master/picture/img.png)

<br></br>
<b>Для её решения вам необходимо изменить настройки в idea</b>
<br></br>
Вот полный путь: File -> Settings -> Editor -> File Encoding -> Global Encoting и Project encoding меняем на Default.
<br></br>
У вас при запуске TODO консоль Idea должна выглядить так:
<br></br>

![Image alt](https://github.com/Daniil600/todo_program/blob/master/picture/img_1.png)

<h2>О проекте</h2>
<h4>Цель</h4>
Этот проект должен был выполняет роль TODO list с сохранением всех задача в xml.
Я старался сделать проект гибким под другие форматы, такие как JSON или YAML.

<h4>Как работает</h4>
<h5>Парсинг</h5>
Для парсинга был выбран parsing DOM. 
Для парсига есть отдельный package с названием <b>"parser"</b>.

<h5>Создание Задач и их изменение</h5>
<br></br>
<b>Логика</b>
Логику приложения выполняет Service.java и имплиментирующие его классы.
<br></br>
<b>Парсинг</b>
Для парсинга был выбран parsing DOM. 
<br></br>
<b>Создание задача</b>
Для создание задач есть отдельный package с названием <b>"service/create"</b>, где находится класс
с необходимыми методами для создания задач пользователем.
<br></br>
<b>Редактирование задача</b>
Для редактирования задач есть отдельный package с названием <b>"service/edit"</b>, где находится класс
с необходимыми методами для изменения задач пользователем.
<br></br>
<b>Редактирование задача</b>

