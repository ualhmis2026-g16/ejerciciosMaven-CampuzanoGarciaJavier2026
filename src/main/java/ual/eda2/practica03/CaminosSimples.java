package ual.eda2.practica03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que proporciona métodos para buscar caminos simples (Hamiltonianos)
 * en un grafo, es decir, caminos que visitan cada vértice al menos una vez y
 * regresan al vértice de origen.
 */
public class CaminosSimples {

    /**
     * Busca todos los caminos simples (Hamiltonianos) en el grafo que parten y
     * regresan al vértice de origen especificado.
     *
     * @param grafo  El grafo en el cual buscar los caminos.
     * @param origen El vértice de origen y destino para los caminos.
     * @return Un mapa que asocia cada camino (lista de vértices) a su peso total.
     */
    public static Map<ArrayList<Vertex>, Double> buscarCaminosSimples(Graph grafo, Vertex origen) {
        Map<ArrayList<Vertex>, Double> conjuntoCaminos = new HashMap<>();
        ArrayList<Vertex> camino = new ArrayList<>();
        ArrayList<Vertex> visitados = new ArrayList<>();

        caminosSimples(grafo, origen, camino, visitados, conjuntoCaminos, origen, 0.0);

        return conjuntoCaminos;
    }

    /**
     * Método recursivo para explorar caminos simples (Hamiltonianos) en el grafo.
     *
     * @param grafo         El grafo en el cual se busca el camino.
     * @param actual        El vértice actual en la exploración.
     * @param camino        Lista que representa el camino recorrido hasta el momento.
     * @param visitados     Lista de vértices ya visitados para asegurar la simplicidad.
     * @param conjuntoCaminos Mapa que almacena los caminos encontrados y su peso total.
     * @param origen        El vértice de origen, al cual se debe regresar para cerrar el ciclo.
     * @param pesoActual    El peso acumulado del camino actual.
     */
    private static void caminosSimples(Graph grafo, Vertex actual, ArrayList<Vertex> camino,
                                       ArrayList<Vertex> visitados, Map<ArrayList<Vertex>, Double> conjuntoCaminos,
                                       Vertex origen, double pesoActual) {
        visitados.add(actual);
        camino.add(actual);

        // Caso base: Si se han visitado todos los vértices y hay un arco que regresa al origen, se encontró un ciclo.
        if (visitados.size() == grafo.getVertices().size() && grafo.adyacentes(actual, origen)) {
            camino.add(origen); // Cerrar el ciclo agregando el origen
            double pesoFinal = pesoActual + grafo.peso(actual, origen); // Sumar el peso del último tramo
            conjuntoCaminos.put(new ArrayList<>(camino), pesoFinal);
            camino.remove(camino.size() - 1); // Quitar la última adición para seguir explorando otros caminos
        } else {
            // Recorrer los vecinos del grafo que no han sido visitados
            for (Vertex vecino : grafo.getVertices()) {
                if (!visitados.contains(vecino) && grafo.adyacentes(actual, vecino)) {
                    double pesoArista = grafo.peso(actual, vecino);
                    caminosSimples(grafo, vecino, camino, visitados, conjuntoCaminos, origen, pesoActual + pesoArista);
                }
            }
        }

        // Retroceder (backtracking)
        camino.remove(camino.size() - 1);
        visitados.remove(visitados.size() - 1);
    }
}