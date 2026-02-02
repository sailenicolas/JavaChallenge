Feature: Solicitud de Token SSO

Background:
    * url urlms
    * header Content-Type = 'application/json'

  @ms-api   @v1.0.0
Scenario: Solicitud Guardado

    Given path "/ms-api/Credits"
    * def ruta = "../../../resources/MS/ms-api/credits/request/Suscripcion.json"
    * def req = read(ruta)
    And request req
    When method POST
    Then status 200
    And match response == read("../../../resources/MS/ms-api/credits/response/GuardadoSuscripcion.json")





