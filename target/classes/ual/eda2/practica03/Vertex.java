package ual.eda2.practica03;

/**
 * Represents a vertex in a graph.
 * Implements Comparable to allow usage in TreeMap and TreeSet.
 */
public class Vertex implements Comparable<Vertex> {
    private String id; // Identifier for the vertex
    
    /**
     * Constructor with vertex identifier
     * @param id The identifier for this vertex
     */
    public Vertex(String id) {
        this.id = id;
    }
    
    /**
     * Gets the vertex ID
     * @return The identifier of this vertex
     */
    public String getId() {
        return id;
    }
    
    /**
     * Sets the vertex ID
     * @param id The new identifier for this vertex
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Compares this vertex with another vertex.
     * Required for TreeSet/TreeMap operations.
     * @param other The vertex to compare with
     * @return Negative, zero, or positive as this vertex's ID 
     *         is less than, equal to, or greater than the other's
     */
    @Override
    public int compareTo(Vertex other) {
        return this.id.compareTo(other.id);
    }
    
    /**
     * Checks if this vertex equals another object
     * @param obj The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Vertex vertex = (Vertex) obj;
        return id.equals(vertex.id);
    }
    
    /**
     * Generates a hash code for this vertex
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    /**
     * Returns a string representation of this vertex
     * @return A string representation of this vertex
     */
    @Override
    public String toString() {
        return "Vertex[id=" + id + "]";
    }
}