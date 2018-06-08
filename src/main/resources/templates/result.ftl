<html>
<head>
    <meta charset="UTF-8">
    <title>Подарочные наборы</title>
</head>
    <body>

    <#if giftSets?has_content>
        <ul>
            <#list giftSets as giftSet>
                <li>
                    Подарочный набор: ${giftSet.name}
                    <h3>Цена: ${giftSet.price} руб.</h3>
                    <form method="GET">
                        <input type="submit" value="Заказать"/>
                    </form>
                </li>
            </#list>
        </ul>

    <#else>
        <p>No gifts yet</p>
    </#if>
    </body>
</html>