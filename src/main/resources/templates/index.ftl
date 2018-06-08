<html>
    <head>
        <meta charset="UTF-8">
        <title>Подбери подарок другу</title>
    </head>
    <body>
        <form method="GET" action="/gifts/result?gender="$gender"&age="$age">
        <p><label>Категории продуктов в наборе:</label></p>
            <select name=category1>
                <option value="">-</option>
                <#if categories?has_content>
                    <ul>
                        <#list categories as category>
                            <li><option>${category.name}</option></li>
                        </#list>
                    </ul>
                <#else>
                    <p>No categories yet</p>
                </#if>
            </select>
            <select name=category2>
                <option value="">-</option>
                <#if categories?has_content>
                    <ul>
                        <#list categories as category>
                            <li><option>${category.name}</option></li>
                        </#list>
                    </ul>
                <#else>
                    <p>No categories yet</p>
                </#if>
            </select>
            <select name=category3>
                <option value="">-</option>
                <#if categories?has_content>
                    <ul>
                        <#list categories as category>
                            <li><option>${category.name}</option></li>
                        </#list>
                    </ul>
                <#else>
                    <p>No categories yet</p>
                </#if>
             </select>
        <p><label>Пол:</label></p>
            <select name=gender>
                <option value="Женский">Женский</option>
                <option value="Мужской">Мужской</option>
            </select>
        <p><label>Возраст:</label></p>
            <select name=age>
                 <option value="Взрослые">Взрослые</option>
                 <option value="Дети">Дети</option>
            </select>
        <p><label>Сортировка по цене:</label></p>
            <select name=order>
                <option value="ASC">По возрастанию</option>
                <option value="DESC">По убыванию</option>
            </select>
        <p><input type="submit" value="Найти"/></p>
        </form>
    </body>
</html>