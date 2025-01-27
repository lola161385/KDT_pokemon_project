package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pokemon {
    String name;
    ArrayList<String> Ptype;
    String category;
    Map<String, Skill> Pskill;  // 스킬을 Map으로 관리
    int level;
    int hp;
    boolean isEvolved;

    public Pokemon(String name, ArrayList<String> Ptype, String category, Map<String, Skill> Pskill, int level, int hp, boolean isEvolved) {
        this.name = name;
        this.Ptype = Ptype;
        this.category = category;
        this.Pskill = Pskill;
        this.level = level;
        this.hp = hp;
        this.isEvolved = false;
    }

    // 스킬 사용 메서드
    public void useSkill(String skillName, String context) {
        Skill skill = Pskill.get(skillName);
        if (skill != null) {
            if (context.equals("outside") && !skill.canUseSkillOutside()) {
                System.out.println(skillName + " 스킬은 전투 외에서는 사용할 수 없습니다.");
            } else {
                skill.use(context);
            }
        } else {
            System.out.println(this.name + "은(는) 스킬 " + skillName + "을(를) 배우지 않았습니다.");
        }
    }

    // 스킬 이름을 반환하는 메서드
    public List<String> getSkillNames() {
        return new ArrayList<>(Pskill.keySet());
    }

    // 특정 스킬 반환
    public Skill getSkill(String skillName) {
        return Pskill.get(skillName);
    }

    public boolean isEvolved() {
        return isEvolved;
    }

    // 레벨 업 메서드
    public void levelUp() {
        if (this.level < 100) {
            this.level++;
            System.out.println(this.name + "의 레벨이 " + this.level + "로 상승했습니다!");
        } else {
            System.out.println(this.name + "의 레벨은 이미 최고단계입니다 ");
        }
        PokeEvolution.checkAndEvolve(this);
    }

    // 진화 메서드
    public void evolve(boolean isAtMoonlightGarden) {
        if (!isEvolved) {
            if (isAtMoonlightGarden) {
                isEvolved = true;
                System.out.println(name + "이(가) 달맞이 동산에서 진화했습니다!");
            } else {
                System.out.println(name + "은(는) 달맞이 동산에 도착하지 않아 아직 진화할 수 없습니다.");
            }
        } else {
            System.out.println(name + "은(는) 이미 진화한 포켓몬입니다.");
        }
    }
}