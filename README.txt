Мини - заметка.

Замечу, что иногда фигура не ставится на поле, хотя была туда перетащена.
Это не баг, так происходит, кода курсор мыши при отпускании наведен ровно между клетками
(то есть попал в сетку). Это рассчитывается как неуверенный ход и не засчитывается.

Кроме того, можно заметить, что в качестве моделей выступают графические элементы (точнее классы-наследники от них).
Почему так?
В данной задаче полезно инкапсулировать отображение элементов (поля, фигур, клеток) внутри
классов-моделей, позволяя контроллеру взаимодействовать непосредственно с ними. Таким образом, обновляя
модель, сразу же обновляется отображение. Это логично и удобно, ведь все данные приложения - где расположен каждый
элемент и в каком он состоянии.