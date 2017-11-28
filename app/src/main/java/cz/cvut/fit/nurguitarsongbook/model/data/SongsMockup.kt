package cz.cvut.fit.nurguitarsongbook.model.data

import cz.cvut.fit.nurguitarsongbook.model.entity.Song

/**
 * Created by tomas on 26.11.2017.
 */
object SongsMockup {
    val songs: MutableList<Song> by lazy {
        val list = ArrayList<Song>()
        for (i in 1..10) {
            list.add(Song(DataMockup.getSongId(), "name ${i}", "artist ${i}", "comment ${i}", "text ${i}", ArrayList()))
        }
        list.add(Song(DataMockup.getSongId(), "Highway to Hell", "AC/DC", "Moje oblibena <3", "(A, D, G, D, G, D, G, D, A)\n" +
            "\n" +
            "\n" +
            "\n" +
            "Livin' easy, livin' free, season ticket on a one-way ride\n" +
            "\n" +
            "Askin' nothin', leave me be, takin' everythin' in my stride\n" +
            "\n" +
            "Don't need reason, don't need rhyme\n" +
            "\n" +
            "Ain't nothing that I'd rather do\n" +
            "\n" +
            "Goin' down, party time, my friends are gonna be there too\n" +
            "\n" +
            "\n" +
            "\n" +
            "(Em, A)I'm on the highway to hell(D, G, D)\n" +
            "\n" +
            "(A)Highway to Hell(D, G, D)\n" +
            "\n" +
            "(A)I'm on the highway to hell(D)\n" +
            "\n" +
            "\n" +
            "\n" +
            "(A, D, G, D, G, D, G, D, A)\n" +
            "\n" +
            "No stop signs, speed limit, nobody's gonna slow me down\n" +
            "\n" +
            "Like a wheel, gonna spin it, nobody's gonna mess me around\n" +
            "\n" +
            "Hey Satan, payin' my dues, playin' in a rockin' band\n" +
            "\n" +
            "Hey Mumma, look at me, I'm on my way to the promised land\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "(Em, A)I'm on the highway to hell(D, G, D)\n" +
            "\n" +
            "(A)Highway to Hell(D, G, D)\n" +
            "\n" +
            "(A)I'm on the highway to hell(D, G)\n" +
            "\n" +
            "\n" +
            "\n" +
            "Don't stop me\n" +
            "\n" +
            "\n" +
            "\n" +
            "(A)I'm on the highway to hell(D, G, D)\n" +
            "\n" +
            "(A)On the highway to hell(D, G, D)\n" +
            "\n" +
            "(A)Highway to Hell(D, G)\n" +
            "\n" +
            "(A)I'm on the highway to hell(D, G)\n" +
            "\n" +
            "\n" +
            "\n" +
            "And I'm going down, all the way\n" +
            "\n" +
            "I'm on the highway to hell\n", ArrayList()))
        list.add(Song(DataMockup.getSongId(), "Rap God", "Eminem", "hehehe", "Look, I was gonna go easy on you and not to hurt your feelings\n" +
            "But I'm only going to get this one chance\n" +
            "Something's wrong, I can feel it (Six minutes, Slim Shady, you're on)\n" +
            "Just a feeling I've got, like something's about to happen, but I don't know what\n" +
            "If that means, what I think it means, we're in trouble, big trouble,\n" +
            "And if he is as bananas as you say, I'm not taking any chances\n" +
            "You were just what the doctor ordered\n" +
            "\n" +
            "I'm beginning to feel like a Rap God, Rap God\n" +
            "All my people from the front to the back nod, back nod\n" +
            "Now who thinks their arms are long enough to slap box, slap box?\n" +
            "They said I rap like a robot, so call me Rapbot\n" +
            "\n" +
            "But for me to rap like a computer must be in my genes\n" +
            "I got a laptop in my back pocket\n" +
            "My pen'll go off when I half-cock it\n" +
            "Got a fat knot from that rap profit\n" +
            "Made a living and a killing off it\n" +
            "Ever since Bill Clinton was still in office\n" +
            "With Monica Lewinsky feeling on his nut-sack\n" +
            "I'm an MC still as honest\n" +
            "But as rude and indecent as all hell syllables, killaholic (Kill 'em all with)\n" +
            "This slickety, gibbedy, hibbedy hip hop\n" +
            "You don't really wanna get into a pissing match with this rappidy rap\n" +
            "Packing a Mac in the back of the Ac, pack backpack rap, yep, yackidy-yac\n" +
            "The exact same time I attempt these lyrical acrobat stunts while I'm practicing\n" +
            "That I'll still be able to break a motherfuckin' table\n" +
            "Over the back of a couple of faggots and crack it in half\n" +
            "Only realized it was ironic I was signed to Aftermath after the fact\n" +
            "How could I not blow? All I do is drop F-bombs, feel my wrath of attack\n" +
            "Rappers are having a rough time period, here's a Maxipad\n" +
            "It's actually disastrously bad\n" +
            "For the wack while I'm masterfully constructing this masterpiece as\n" +
            "\n" +
            "I'm beginning to feel like a Rap God, Rap God\n" +
            "All my people from the front to the back nod, back nod\n" +
            "Now who thinks their arms are long enough to slap box, slap box?\n" +
            "Let me show you maintaining this shit ain't that hard, that hard\n" +
            "\n" +
            "Everybody want the key and the secret to rap immortality like I have got\n" +
            "Well, to be truthful the blueprint's simply rage and youthful exuberance\n" +
            "Everybody loves to root for a nuisance\n" +
            "Hit the earth like an asteroid, did nothing but shoot for the moon since\n" +
            "MC's get taken to school with this music\n" +
            "Cause I use it as a vehicle to bust a rhyme\n" +
            "Now I lead a new school full of students\n" +
            "Me? I'm a product of Rakim, Lakim Shabazz, 2Pac N-\n" +
            "-W.A, Cube, hey, Doc, Ren, Yella, Eazy, thank you, they got Slim\n" +
            "Inspired enough to one day grow up, blow up and be in a position\n" +
            "To meet Run DMC and induct them into the motherfuckin' Rock n'\n" +
            "Roll Hall of Fame\n" +
            "Even though I walk in the church and burst in a ball of flames\n" +
            "Only Hall of Fame I be inducted in is the alcohol of fame\n" +
            "On the wall of shame\n" +
            "You fags think it's all a game 'til I walk a flock of flames\n" +
            "Off of planking, tell me what in the fuck are you thinking?\n" +
            "Little gay looking boy\n" +
            "So gay I can barely say it with a straight face looking boy\n" +
            "You witnessing a massacre\n" +
            "Like you watching a church gathering take place looking boy\n" +
            "Oy vey, that boy's gay, that's all they say looking boy\n" +
            "You get a thumbs up, pat on the back\n" +
            "And a way to go from your label everyday looking boy\n" +
            "Hey, looking boy, what you say looking boy?\n" +
            "I got a \"hell yeah\" from Dre looking boy\n" +
            "I'mma work for everything I have\n" +
            "Never ask nobody for shit, get outta my face looking boy\n" +
            "Basically boy you're never gonna be capable\n" +
            "To keep up with the same pace looking boy\n" +
            "\n" +
            "'Cause I'm beginning to feel like a Rap God, Rap God\n" +
            "All my people from the front to the back nod, back nod\n" +
            "The way I'm racing around the track, call me Nascar, Nascar\n" +
            "Dale Earnhardt of the trailer park, the White Trash God\n" +
            "Kneel before General Zod this planet's Krypton, no Asgard, Asgard\n" +
            "\n" +
            "So you be Thor and I'll be Odin, you rodent, I'm omnipotent\n" +
            "Let off then I'm reloading immediately with these bombs I'm totin'\n" +
            "And I should not be woken\n" +
            "I'm the walking dead, but I'm just a talking head, a zombie floating\n" +
            "But I got your mom deep throating\n" +
            "I'm out my ramen noodle, we have nothing in common, poodle\n" +
            "I'm a doberman, pinch yourself in the arm and pay homage, pupil\n" +
            "It's me, my honesty's brutal\n" +
            "But it's honestly futile if I don't utilize what I do though\n" +
            "For good at least once in a while\n" +
            "So I wanna make sure somewhere in this chicken scratch I scribble and doodle\n" +
            "Enough rhymes to maybe to try and help get some people through tough times\n" +
            "But I gotta keep a few punchlines just in case cause even you unsigned\n" +
            "Rappers are hungry looking at me like it's lunchtime\n" +
            "I know there was a time where once I\n" +
            "Was king of the underground, but I still rap like I'm on my Pharoahe Monch grind\n" +
            "So I crunch rhymes, but sometimes when you combine\n" +
            "Appeal with the skin color of mine\n" +
            "You get too big and here they come trying to,\n" +
            "Censor you like that one line I said on \"I'm Back\" from the Marshall Mathers LP\n" +
            "One where I tried to say I take seven kids from Columbine\n" +
            "Put 'em all in a line, add an AK-47, a revolver and a nine\n" +
            "See if I get away with it now that I ain't as big as I was, but I've\n" +
            "Morphed into an immortal coming through the portal\n" +
            "You're stuck in a time warp from 2004 though\n" +
            "And I don't know what the fuck that you rhyme for\n" +
            "You're pointless as Rapunzel with fucking cornrows\n" +
            "You're like normal, fuck being normal\n" +
            "And I just bought a new Raygun from the future\n" +
            "To just come and shoot ya like when Fabolous made Ray J mad\n" +
            "'Cause Fab said he looked like a fag at Maywhether's pad\n" +
            "Singin' to a man while they played piano\n" +
            "Man, oh man, that was a 24/7 special on the cable channel\n" +
            "So Ray J went straight to the radio station the very next day\n" +
            "\"Hey, Fab, I'mma kill you\"\n" +
            "Lyrics coming at you at supersonic speed, (JJ Fad)\n" +
            "Uh, sama lamaa duma lamaa you assuming I'm a human\n" +
            "What I gotta do to get it through to you I'm superhuman\n" +
            "Innovative and I'm made of rubber\n" +
            "So that anything you saying ricocheting off of me and it'll glue to you\n" +
            "I'm never stating, more than never demonstrating\n" +
            "How to give a motherfuckin' audience a feeling like it's levitating\n" +
            "Never fading, and I know that the haters are forever waiting\n" +
            "For the day that they can say I fell off, they'd be celebrating\n" +
            "Cause I know the way to get 'em motivated\n" +
            "I make elevating music, you make elevator music\n" +
            "Oh, he's too mainstream\n" +
            "Well, that's what they do when they get jealous, they confuse it\n" +
            "It's not hip hop, it's pop, cause I found a hella way to fuse it\n" +
            "With rock, shock rap with Doc\n" +
            "Throw on Lose Yourself and make 'em lose it\n" +
            "I don't know how to make songs like that\n" +
            "I don't know what words to use\n" +
            "Let me know when it occurs to you\n" +
            "While I'm ripping any one of these verses diverse as you\n" +
            "It's curtains, I'm inadvertently hurtin' you\n" +
            "How many verses I gotta murder to,\n" +
            "Prove that if you're half as nice at songs you can sacrifice virgins too uh!\n" +
            "School flunkie, pill junky\n" +
            "But look at the accolades the skills brung me\n" +
            "Full of myself, but still hungry\n" +
            "I bully myself cause I make me do what I put my mind to\n" +
            "And I'm a million leagues above you, ill when I speak in tongues\n" +
            "But it's still tongue in cheek, fuck you\n" +
            "I'm drunk so Satan take the fucking wheel, I'm asleep in the front seat\n" +
            "Bumping Heavy D and the Boys, still chunky, but funky\n" +
            "But in my head there's something I can feel tugging and struggling\n" +
            "Angels fight with devils, here's what they want from me\n" +
            "They asking me to eliminate some of the women hate\n" +
            "But if you take into consideration the bitter hatred that I had\n" +
            "Then you may be a little patient and more sympathetic to the situation\n" +
            "And understand the discrimination\n" +
            "But fuck it, life's handing you lemons, make lemonade then\n" +
            "But if I can't batter the women how the fuck am I supposed to bake them a cake then?\n" +
            "Don't mistake it for Satan\n" +
            "It's a fatal mistake if you think I need to be overseas\n" +
            "And take a vacation to trip a broad\n" +
            "And make her fall on her face and don't be a retard\n" +
            "Be a king? Think not, why be a king when you can be a God?", ArrayList()))
        list
    }

}