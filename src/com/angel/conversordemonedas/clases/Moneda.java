package com.angel.conversordemonedas.clases;
//Esta clase genera automáticamente métodos como constructor, getters , equals(), hashCode()
// y toString(). Por lo tanto, es ideal para modelar agregados de datos simples.
public record Moneda(String base_code,
                     String target_code,
                     double conversion_rate) {
}
