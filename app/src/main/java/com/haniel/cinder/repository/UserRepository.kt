package com.haniel.cinder.model

import com.haniel.cinder.R

class UserRepository{

    private val data = mutableListOf<User>(
        User("1","Karla Fernades","1234", 24, R.drawable.karla_fernades,"Oi, sou Karla Fernandes, uma estudante universitária cursando Engenharia de Computação.\n Desde criança, sempre fui fascinada por tecnologia e como ela pode mudar o mundo.\n Atualmente, estou no terceiro ano do curso e adoro desafios de programação e projetos práticos que envolvem inovação tecnológica. \n Gostaria de conhecer alguém com interesses semelhantes, que valorize tanto uma conversa sobre as últimas tendências em tecnologia quanto um passeio ao ar livre. Acredito que encontrar um parceiro que compartilhe minhas paixões e objetivos pode tornar a jornada ainda mais emocionante e gratificante."),
        User("2","Mary Lopez","1234", 31,  R.drawable.mary_lopez,"Sou professora do IFPB. Tenho Mestrado em Literatura Inglesa e leciono há 4 anos. Minha área de especialização é a literatura vitoriana, e adoro compartilhar meu conhecimento e entusiasmo por esse período literário com meus alunos."),
        User("3","Sussan Eliane","1234", 26,  R.drawable.susan_eliane,"Sou Sussan, uma artista plástica dedicada que encontrou na pintura a minha verdadeira vocação. Graduei-me em Belas Artes e me especializei em arte contemporânea, com obras expostas em diversas galerias ao redor do mundo. Sou conhecida por minha habilidade em combinar cores vibrantes e texturas únicas, criando peças que capturam a atenção e a imaginação do público. Além de pintar, gosto de ensinar arte a crianças e jovens na minha comunidade."),
        User("4","Rebeka Andrade","12342",9,  R.drawable.rebeka_andrade,"Olá! Sou uma engenheira de software apaixonada por tecnologia e inovação. Desde pequena, sempre fui fascinada por computadores e pela maneira como eles funcionam. Sou formada em Ciência da Computação pela Universidade Federal e atualmente trabalho em uma startup de tecnologia, desenvolvendo soluções inovadoras para problemas cotidianos. Nas minhas horas vagas, adoro ler livros de ficção científica e explorar novos lugares com minha câmera fotográfica."),
    )

    operator fun get(index: Int): User {
        return data[index % data.size]
    }

    fun getPerson(index: Int): User {
        return data[index % data.size]
    }

    fun size():Int{
        return data.size
    }
}