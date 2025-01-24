package pokemon;

public interface Skill {
    String getName();
    boolean canUseSkillOutside();
    void use(String context);


}
