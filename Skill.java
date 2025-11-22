
// Skill.java
public enum Skill {
    HEAL("Cura", 0),
    HEAVY("Ataque Pesado", 1),
    DEFEND("Defesa", 2);

    private final String nome;
    private final int id;
    Skill(String nome, int id) { this.nome = nome; this.id = id; }
    public String getNome() { return nome; }
    public int getId() { return id; }
}
