package com.ndrewcoding.ichnofossil.util;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import com.ndrewcoding.ichnofossil.model.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DummyData {

    private final PostRepository postRepository;

//    @PostConstruct
    public void savePosts() {
        List<Post> posts = new ArrayList<>();

        Post postOne = Post.builder()
                .author("Andrew Monteiro")
                .title("German Separable Verbs")
                .releaseDate(LocalDate.now())
                .text("Mussum Ipsum, cacilds vidis litro abertis. Sapien in monti palavris qui num significa nadis i pareci latim. Não sou faixa preta cumpadi, sou preto inteiris, inteiris. Quem num gosta di mé, boa gentis num é. Mauris nec dolor in eros commodo tempor. Aenean aliquam molestie leo, vitae iaculis nisl.\n" +
                        "\n" +
                        "Aenean aliquam molestie leo, vitae iaculis nisl. Mais vale um bebadis conhecidiss, que um alcoolatra anonimis. Pra lá , depois divoltis porris, paradis. In elementis mé pra quem é amistosis quis leo.\n" +
                        "\n" +
                        "Paisis, filhis, espiritis santis. Detraxit consequat et quo num tendi nada. Per aumento de cachacis, eu reclamis. Suco de cevadiss deixa as pessoas mais interessantis.")
                .build();

        Post postTwo = Post.builder()
                .author("Andrew Monteiro")
                .title("The Physics of a Rolling Ball")
                .releaseDate(LocalDate.now())
                .text("Mussum Ipsum, cacilds vidis litro abertis. Admodum accumsan disputationi eu sit. Vide electram sadipscing et per. Suco de cevadiss deixa as pessoas mais interessantis. Mé faiz elementum girarzis, nisi eros vermeio. Quem manda na minha terra sou euzis!\n" +
                        "\n" +
                        "Detraxit consequat et quo num tendi nada. Mais vale um bebadis conhecidiss, que um alcoolatra anonimis. Per aumento de cachacis, eu reclamis. Delegadis gente finis, bibendum egestas augue arcu ut est.\n" +
                        "\n" +
                        "A ordem dos tratores não altera o pão duris. Tá deprimidis, eu conheço uma cachacis que pode alegrar sua vidis. Quem num gosta di mim que vai caçá sua turmis! Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis.")
                .build();

        posts.add(postOne);
        posts.add(postTwo);

        for (Post post : posts) {
            Post savedPost = postRepository.save(post);
            System.out.println(savedPost.getId());
        }
    }
}
