[Тестовое задание.pdf](https://disk.yandex.ru/i/xIbh29RVktbiLg)

Не реализовано:
1) Требование №8 "Если в запросе есть наименование колонки, которой нет в таблице -
   выдать Exception. Также если в сравнении участвует тип, который не поддерживается данным оператором выкинуть также Exception ( например ‘lastName’>10"
2) Требование №12 "Наименование колонок оборачиваются в одинарные
   кавычки." Сделал колонки без кавычек;
3) Требования №13 и №14 "Сравнение с null";
4) Добавил к команде select звезду - '*', обозначающую все колонки таблицы;
5) Операторы like и ilike;

## Еще пару слов по поводу реализации
В результате работы написан лексер, парсер и транслятор. Вся грамматика описывается в парсере.
В папке ast/ расположены узлы, необходимые для парсера, который строит из них асбтрактное синтаксическое дерево.
Написание каждой из сущностей, за исключением транслятора, сопровождалось созданием тестов, которые были удалены из финальной реализации для соответствия №17 пункту требований,
потому что используется стороння библиотека JUnit.