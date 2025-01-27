package pokemon;

public class PokeEvolution {
    // 포켓몬 진화 조건 확인 및 처리
    public static void checkAndEvolve(Pokemon pokemon) {
        Pokedex pokedexData = Pokedex.PokedexData.getPokemon(pokemon.name);
        if (pokedexData.getCategory().equals("달")) {
            System.out.printf("진화조건 불충족 진화뷸가");
        }else {
            if (pokedexData != null && pokemon.level >= pokedexData.getEvolvLevel() && !pokedexData.getNextEvolv().equals("최종 진화 단계")) {
                evolve(pokemon, pokedexData.getNextEvolv());
            }
        }

    }

    // 달맞이 동산에 도착했을 때 달 속성 포켓몬 진화 처리
    public static void ArriveEvolve(Trainer trainer) {
        System.out.println("\n=== 달맞이 동산에 도착했습니다! ===");
        System.out.println("달 속성을 가진 포켓몬이 진화할 수 있는지 확인합니다.");

        for (Pokemon pokemon : trainer.getMyPokemon()) {
            // 포켓몬의 타입 중 "달"이 포함되어 있는지 확인
            if (pokemon.category.contains("달")) {
                System.out.println(pokemon.name + "은(는) 달 속성을 가지고 있습니다. 진화를 시도합니다.");
                Pokedex pokedexData = Pokedex.PokedexData.getPokemon(pokemon.name);
                evolve(pokemon, pokedexData.getNextEvolv());
            } else {
                System.out.println(pokemon.name + "은(는) 달 속성을 가지고 있지 않아 진화할 수 없습니다.");
            }
        }
    }

    // 포켓몬 진화 처리
    private static void evolve(Pokemon pokemon, String nextEvolv) {
        Pokedex nextPokedexData = Pokedex.PokedexData.getPokemon(nextEvolv);
        if (nextPokedexData != null) {
            System.out.println("...... 오잉!? " + pokemon.name + "의 상태가.......!\n 축하합니다! " + nextEvolv + "(으)로 진화했습니다!");

            // 포켓몬 정보 업데이트
            pokemon.name = nextPokedexData.getName();
            pokemon.Ptype = nextPokedexData.getTypes();
            pokemon.category = nextPokedexData.getCategory();
        }
    }
}