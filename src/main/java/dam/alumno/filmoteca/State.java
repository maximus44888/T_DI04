package dam.alumno.filmoteca;

public enum State {
    SHOW("Ver película"),
    ADD("Crear película"),
    UPDATE("Modificar película"),
    ;

    public final String value;

    State(final String value) {
        this.value = value;
    }
}
