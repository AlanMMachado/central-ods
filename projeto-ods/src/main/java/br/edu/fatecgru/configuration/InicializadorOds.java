package br.edu.fatecgru.configuration;

import br.edu.fatecgru.model.OdsEntity;
import br.edu.fatecgru.repository.OdsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class InicializadorOds {

    @Bean
    CommandLineRunner initDatabase(OdsRepository odsRepository) {
        return args -> {
            // Verifica se já existem ODS cadastradas
            if (odsRepository.count() == 0) {
                List<OdsEntity> odsList = Arrays.asList(
                        new OdsEntity("Erradicação da Pobreza"),
                        new OdsEntity("Fome Zero e Agricultura Sustentável"),
                        new OdsEntity("Saúde e Bem-Estar"),
                        new OdsEntity("Educação de Qualidade"),
                        new OdsEntity("Igualdade de Gênero"),
                        new OdsEntity("Água Potável e Saneamento"),
                        new OdsEntity("Energia Limpa e Acessível"),
                        new OdsEntity("Trabalho Decente e Crescimento Econômico"),
                        new OdsEntity("Indústria, Inovação e Infraestrutura"),
                        new OdsEntity("Redução das Desigualdades"),
                        new OdsEntity("Cidades e Comunidades Sustentáveis"),
                        new OdsEntity("Consumo e Produção Responsáveis"),
                        new OdsEntity("Ação Contra a Mudança Global do Clima"),
                        new OdsEntity("Vida na Água"),
                        new OdsEntity("Vida Terrestre"),
                        new OdsEntity("Paz, Justiça e Instituições Eficazes"),
                        new OdsEntity("Parcerias e Meios de Implementação")
                );

                odsRepository.saveAll(odsList);
                System.out.println("17 ODS cadastradas com sucesso!");
            }
        };
    }
}