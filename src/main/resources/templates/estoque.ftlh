<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>
  <body>
    <p>Os produtos abaixo estão com estoque abaixo do estoque mínimo:</p>
    <#list produtos>
      <ul>
        <#items as produto>
          <li><strong>${produto.nome}(${produto.quantidade})</strong></li>
        </#items>
      </ul>
    </#list>
    <div>
      Atenciosamente,<br />
      <strong>EstoqueApp</strong>
    </div>
  </body>
</html>
