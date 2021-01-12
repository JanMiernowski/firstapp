package pl.sda.finalapp.exceprions;

public class WrongIdException extends Exception{

    public WrongIdException(Integer id1, Integer id2){
        super(id2 + " must be the same as " + id1);
    }

}
