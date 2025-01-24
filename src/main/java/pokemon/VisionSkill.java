package pokemon;

public class VisionSkill implements Skill, OceanCrossable {
    private String name;
    private int damage;
    private String description;
    private String UseSkillIsNotBattle;

    public VisionSkill(String name, int damage, String description, String UseSkillIsNotBattle) {
        this.name = name;
        this.damage = damage;
        this.description = description;
        this.UseSkillIsNotBattle = UseSkillIsNotBattle;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean canUseSkillOutside() {
        return true;
    }

    @Override
    public void use(String context) {
        if (context.equals("outside")) {
            System.out.println(name + " 스킬 사용: " + UseSkillIsNotBattle);
            if (canCrossOcean()) {
                System.out.println("다른 마을로 이동합니다!");
            }
        } else {
            System.out.println(name + " 스킬 사용! Effect: " + description + ", Damage: " + damage);
        }
    }

    @Override
    public boolean canCrossOcean() {
        return name.equals("파도타기") || name.equals("공중날기");
    }

    public int getDamage() {
        return damage;
    }
}