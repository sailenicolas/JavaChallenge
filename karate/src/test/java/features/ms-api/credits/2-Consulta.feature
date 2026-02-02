Feature: MS  - Consulta Credits

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.28
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/Credits"
  * def respuesta =  call read("./GuardadoConsulta.feature")
  * param id = respuesta.response.data.id
  When method get
  Then status <Vstatus>
  And match response == read("../../../resources/MS/ms-api/credits/response/<Vresponse>")
  Examples:
    |read("../../../resources/MS/ms-api/credits/ConsultaSuscripcion.csv")|

  @ms-api   @v1.0.28
Scenario: 138288 - Credits / Consulta - No ingresar parámetro ID

  Given path "/ms-api/Credits"
  When method get
  Then status 400
  And match response == read("../../../resources/MS/ms-api/credits/response/IdNulo.json")

@ms-api   @v1.0.28
Scenario: 138289 - Credits / Consulta - Ingresar data no existente

  Given path "/ms-api/Credits"
  And param id = "27003890"
  When method get
  Then status 404
   