import java.io.FileWriter;
import java.time.LocalDateTime;

public class ReceiptGenerator {

    public static void generate(String message){

        try{

            FileWriter writer = new FileWriter("receipt.txt",true);

            writer.write("----- ATM RECEIPT -----\n");
            writer.write(LocalDateTime.now()+"\n");
            writer.write(message+"\n");
            writer.write("-----------------------\n\n");

            writer.close();

        }catch(Exception e){

            System.out.println("Receipt error");
        }
    }
}