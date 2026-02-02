Feature: MS  - Guardar Credits

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.0
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/Credits"
  * def ruta = "../../../resources/MS/ms-api/credits/request/<Vjson>"
  * def req = read(ruta)
  And request req
  When method POST
  Then status <Vstatus>
  And match response == read("../../../resources/MS/ms-api/credits/response/<Vresponse>")
  Examples:
    |read("../../../resources/MS/ms-api/credits/GuardarSuscripcion.csv")|

  @ms-api   @v1.0.0
Scenario: 138277 -  Credits / Ingresar método no permitido

  Given path "/ms-api/Credits"
  * def ruta = "../../../resources/MS/ms-api/credits/request/Suscripcion.json"
  * def req = read(ruta)
  And request req
  And param rutEmpresa = "26391790-1"
  And param tefType = "ALL"
  When method PATCH
  Then status 405
  And match response == read("../../../resources/MS/ms-api/credits/response/MétodoIncorrecto.json")

  @ms-api   @v1.0.0
Scenario: 138279 -  Credits / Guardado - Ingresar campos requeridos vacíos

  Given path "/ms-api/Credits"
  * def ruta = "../../../resources/MS/ms-api/credits/request/CamposVacios.json"
  * def req = read(ruta)
  And request req
  When method POST
  Then status 400

  @ms-api   @v1.0.0
Scenario: 138280 -  Credits / Guardado - No ingresar campos requeridos

  Given path "/ms-api/Credits"
  * def ruta = "../../../resources/MS/ms-api/credits/request/CamposNulos.json"
  * def req = read(ruta)
  And request req
  When method POST
  Then status 400

  @ms-api   @v1.0.0
Scenario: 138282 -  Credits / Guardado - Ingresar campos vacíos y formato incorrecto

  Given path "/ms-api/Credits"
  * def ruta = "../../../resources/MS/ms-api/credits/request/CamposVaciosFormatoIncorrecto.json"
  * def req = read(ruta)
  When method POST
  Then status 400

  @ms-api   @v1.0.0
Scenario: 138278 -  Credits / Ingresar content type no soportado

  Given path "/ms-api/Credits"
  * header Content-Type = 'text/html'
  * def ruta = "../../../resources/MS/ms-api/credits/request/Suscripcion.json"
  * def req = read(ruta)
  And request req
  When method POST
  Then status 415
  And match response == read("../../../resources/MS/ms-api/credits/response/ContentTypeIncorrecto.json")
