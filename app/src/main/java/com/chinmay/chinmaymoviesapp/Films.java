package com.chinmay.chinmaymoviesapp;

class Films {
    public String getFilm_name() {
        return Film_name;
    }

    public String getFilm_Overview() {
        return Film_Overview;
    }

    public String getFilm_Popularity() {
        return Film_Popularity;
    }

    public String getFilm_imgid() {
        return Film_imgid;
    }

    public String getFilm_publishDate() {
        return Film_publishDate;
    }



    public Films(String film_name, String film_Overview, String film_Popularity, String film_imgid, String film_publishDate) {
        Film_name = film_name;
        Film_Overview = film_Overview;
        Film_Popularity = film_Popularity;
        Film_imgid = film_imgid;
        Film_publishDate = film_publishDate;
    }

    private final String Film_Overview;
    private final String Film_name;
    private final String Film_Popularity;
    private final String Film_imgid;
    private final String Film_publishDate;


}
