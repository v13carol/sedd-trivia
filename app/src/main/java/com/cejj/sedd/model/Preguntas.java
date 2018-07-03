package com.cejj.sedd.model;

public class Preguntas {

    private String nivelPregunta, pregunta, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3, tutorial;

    public Preguntas (){

    }

    public Preguntas (String nivelPregunta , String pregunta , String respuestaCorrecta , String respuestaIncorrecta1 , String respuestaIncorrecta2 , String respuestaIncorrecta3 , String tutorial){
        this.nivelPregunta = nivelPregunta;
        this.pregunta = pregunta;
        this.respuestaCorrecta = respuestaCorrecta;
        this.respuestaIncorrecta1 = respuestaIncorrecta1;
        this.respuestaIncorrecta2 = respuestaIncorrecta2;
        this.respuestaIncorrecta3 = respuestaIncorrecta3;
        this.tutorial = tutorial;
    }

    public String getNivelPregunta (){
        return nivelPregunta;
    }

    public void setNivelPregunta (String nivelPregunta){
        this.nivelPregunta = nivelPregunta;
    }

    public String getPregunta (){
        return pregunta;
    }

    public void setPregunta (String pregunta){
        this.pregunta = pregunta;
    }

    public String getRespuestaCorrecta (){
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta (String respuestaCorrecta){
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getRespuestaIncorrecta1 (){
        return respuestaIncorrecta1;
    }

    public void setRespuestaIncorrecta1 (String respuestaIncorrecta1){
        this.respuestaIncorrecta1 = respuestaIncorrecta1;
    }

    public String getRespuestaIncorrecta2 (){
        return respuestaIncorrecta2;
    }

    public void setRespuestaIncorrecta2 (String respuestaIncorrecta2){
        this.respuestaIncorrecta2 = respuestaIncorrecta2;
    }

    public String getRespuestaIncorrecta3 (){
        return respuestaIncorrecta3;
    }

    public void setRespuestaIncorrecta3 (String respuestaIncorrecta3){
        this.respuestaIncorrecta3 = respuestaIncorrecta3;
    }

    public String getTutorial (){
        return tutorial;
    }

    public void setTutorial (String tutorial){
        this.tutorial = tutorial;
    }
}
