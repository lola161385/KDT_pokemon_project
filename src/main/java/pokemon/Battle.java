package pokemon;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Battle {
    public static void start(Trainer trainer1, Trainer trainer2) {
        System.out.println(trainer1.getName() + "이(가) " + trainer2.getName() + "에게 전투를 신청했습니다!");
        System.out.println("전투 시작!");

        Scanner scanner = new Scanner(System.in);
        int myIndex = 0; // 현재 사용 중인 포켓몬의 인덱스
        int oppIndex = 0; // 상대방의 현재 포켓몬 인덱스

        while (myIndex < trainer1.getMyPokemon().size() && oppIndex < trainer2.getMyPokemon().size()) {
            List<Pokemon> pokemons = printPokemonAppear(trainer1, myIndex, trainer2, oppIndex);
            Pokemon myPokemon = pokemons.get(0);
            Pokemon oppPokemon = pokemons.get(1);

            // 내 턴
            myIndex = turn(trainer1, myPokemon, oppPokemon, myIndex, scanner);

            // 상대방의 포켓몬이 쓰러졌는지 확인
            if (oppPokemon.hp <= 0) {
                oppIndex++;
                if (oppIndex >= trainer2.getMyPokemon().size()) {
                    System.out.println(trainer2.getName() + "의 모든 포켓몬이 쓰러졌습니다!");
                    System.out.println(trainer1.getName() + "의 승리!");
                    trainer1.winBattle();
                    return;
                }
                oppPokemon = trainer2.getMyPokemon().get(oppIndex);
                System.out.println(trainer2.getName() + "은(는) 다음 포켓몬으로 " + oppPokemon.name + "을(를) 꺼냈습니다!");
            }

            // 상대 턴
            oppIndex = turn(trainer2, oppPokemon, myPokemon, oppIndex, scanner);

            // 내 포켓몬이 쓰러졌는지 확인
            if (myPokemon.hp <= 0) {
                myIndex++;
                if (myIndex >= trainer1.getMyPokemon().size()) {
                    System.out.println(trainer1.getName() + "의 모든 포켓몬이 쓰러졌습니다!");
                    System.out.println(trainer2.getName() + "의 승리!");
                    trainer2.winBattle();
                    return;
                }
                myPokemon = trainer1.getMyPokemon().get(myIndex);
                System.out.println(trainer1.getName() + "은(는) 다음 포켓몬으로 " + myPokemon.name + "을(를) 꺼냈습니다!");
            }
        }
    }

    private static int turn(Trainer trainer, Pokemon attacker, Pokemon defender, int index, Scanner scanner) {
        System.out.println("\n" + trainer.getName() + "의 차례!");
        System.out.println(attacker.name + "의 스킬: " + attacker.getSkillNames());
        System.out.print("사용할 스킬을 선택하세요: ");
        String skillName = scanner.nextLine();
        attacker.useSkill(skillName, "battle");

        // 상대 포켓몬의 HP 감소
        Skill skill = attacker.getSkill(skillName);
        if (skill != null) {
            if (skill instanceof BattleSkill) {
                defender.hp -= ((BattleSkill) skill).getDamage();
            } else if (skill instanceof VisionSkill) {
                defender.hp -= ((VisionSkill) skill).getDamage();
            }
        }
        System.out.println(defender.name + "의 남은 HP: " + defender.hp);

        return index;
    }

    private static List<Pokemon> printPokemonAppear(Trainer trainer1, int myIndex, Trainer trainer2, int oppIndex) {
        Pokemon myPokemon = trainer1.getMyPokemon().get(myIndex);
        Pokemon oppPokemon = trainer2.getMyPokemon().get(oppIndex);
        System.out.println("\n" + trainer1.getName() + "의 " + myPokemon.name + " vs " + trainer2.getName() + "의 " + oppPokemon.name);
        return Arrays.asList(myPokemon, oppPokemon);
    }
}