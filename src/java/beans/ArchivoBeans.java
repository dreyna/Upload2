/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Estrellita
 */
@ManagedBean
@SessionScoped
public class ArchivoBeans implements Serializable {
    private final String ruta = "C:\\tmp\\";
    public ArchivoBeans() {
    }
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
private void subirarchivo(String filename, InputStream in){
    try {
        OutputStream os = new FileOutputStream(new File(ruta + filename));
        int reader = 0;
        //byte[] bytes;
        //bytes = new byte[(int)getFile().getSize()];
        byte[] bytes = new byte[(int) getFile().getSize()];
        while((reader = in.read(bytes)) != -1){
          os.write(bytes, 0, reader);
        }
        in.close();
        os.flush();
        os.close();
    } catch (Exception e) {
    }

}
    public void upload() {
        String extValidate=null, ext=null;
        if (file!= null) {
            ext = getFile().getFileName();
              if (ext != null) {
                extValidate = ext.substring(ext.indexOf(".") + 1);
            } else {
                extValidate = "null";
            }
              if (extValidate.equals("jpg")) {
                try {
                    subirarchivo(getFile().getFileName(), getFile().getInputstream());
                } catch (IOException e) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Error", "Un problema ocurrió al enviar el archivo!"));
                }

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucess", getFile().getFileName() + "Se envió el archivo correctamente!"));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", "Extensión del archivo no permitida!"));
            }
        }else{
            FacesMessage message = new FacesMessage("Error: ", ext + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        
        }
    }
}
