package ual.eda2.practica02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias para la clase {@link Vertex}.
 * Verifica el correcto funcionamiento de los métodos principales de la clase,
 * incluyendo construcción, modificación, comparación y representación.
 */
class VertexTest {

    /**
     * Verifica que el constructor y el método {@code getId()} funcionen correctamente.
     * Crea un vértice con un ID específico y comprueba que se recupere correctamente.
     */
    @Test
    void testConstructorAndGetId() {
        Vertex v = new Vertex("Madrid");
        assertEquals("Madrid", v.getId());
    }

    /**
     * Verifica el método {@code setId()}.
     * Cambia el ID del vértice y comprueba que se actualice correctamente.
     */
    @Test
    void testSetId() {
        Vertex v = new Vertex("Madrid");
        v.setId("Barcelona");
        assertEquals("Barcelona", v.getId());
    }

    /**
     * Verifica el método {@code equals()}.
     * Comprueba que dos vértices con el mismo ID se consideren iguales
     * y que dos vértices con diferentes ID no lo sean.
     */
    @Test
    void testEquals() {
        Vertex v1 = new Vertex("Madrid");
        Vertex v2 = new Vertex("Madrid");
        Vertex v3 = new Vertex("Barcelona");

        assertTrue(v1.equals(v2));
        assertFalse(v1.equals(v3));
    }

    /**
     * Verifica el método {@code hashCode()}.
     * Asegura que dos vértices iguales tengan el mismo hash code
     * y que dos vértices diferentes tengan hash codes distintos.
     */
    @Test
    void testHashCode() {
        Vertex v1 = new Vertex("Madrid");
        Vertex v2 = new Vertex("Madrid");
        Vertex v3 = new Vertex("Barcelona");

        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1.hashCode(), v3.hashCode());
    }

    /**
     * Verifica el método {@code compareTo()} basado en orden lexicográfico.
     * Comprueba que el orden sea el correcto entre dos IDs diferentes.
     */
    @Test
    void testCompareTo() {
        Vertex v1 = new Vertex("Madrid");
        Vertex v2 = new Vertex("Barcelona");

        assertTrue(v1.compareTo(v2) > 0);  // "Madrid" > "Barcelona"
        assertTrue(v2.compareTo(v1) < 0);  // "Barcelona" < "Madrid"
    }

    /**
     * Verifica el método {@code toString()}.
     * Comprueba que se genere correctamente la representación en texto del vértice.
     */
    @Test
    void testToString() {
        Vertex v = new Vertex("Madrid");
        assertEquals("Vertex[id=Madrid]", v.toString());
    }
}
