import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Calendar;

public class Ventana extends JPanel implements Runnable {
    private final BufferedImage puerroSegundos;
    private final BufferedImage puerroMinutos;
    private final BufferedImage puerroHoras;
    private final BufferedImage fondo;
    private BufferedImage rotatedMinutos;
    private BufferedImage rotatedHoras;
    private BufferedImage rotatedSegundos;
    private int hora;
    private int min;
    private int sec;


    public Ventana() {
        Dimension dimension = new Dimension(1000, 1000);
        setPreferredSize(dimension);

        hora = Calendar.HOUR;
        min = Calendar.MINUTE;
        sec = Calendar.SECOND;
        //Fondo del reloj
        fondo = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics gfondo = fondo.getGraphics();
        ImageIcon iconoRelojFondo = new ImageIcon(getClass().getResource("img/clock_hatsune_miku.png"));
        Image img = iconoRelojFondo.getImage();
        gfondo.drawImage(img, 0, -50, 1000, 1000, null);
        gfondo.dispose();

        //Minutero del reloj
        puerroMinutos = new BufferedImage(467, 350, BufferedImage.TYPE_INT_ARGB);
        Graphics gMinutos = puerroMinutos.getGraphics();
        ImageIcon iconoMinutos = new ImageIcon(getClass().getResource("img/leek_minutos.png"));
        Image imgMinutos = iconoMinutos.getImage();
        gMinutos.drawImage(imgMinutos, 0, 0, 467, 350, null);
        gMinutos.dispose();


        //Horario del reloj
        puerroHoras = new BufferedImage(276, 207, BufferedImage.TYPE_INT_ARGB);
        Graphics gHoras = puerroHoras.getGraphics();
        ImageIcon iconoHoras = new ImageIcon(getClass().getResource("img/leek_horas.png"));
        Image imgHoras = iconoHoras.getImage();    //350       200
        gHoras.drawImage(imgHoras, 0, 0, 276, 207, null);
        gHoras.dispose();

        //Segundero del reloj
        puerroSegundos = new BufferedImage(960,720,BufferedImage.TYPE_INT_ARGB);
        Graphics gSegundos = puerroSegundos.getGraphics();
        ImageIcon iconoSegundos = new ImageIcon(getClass().getResource("img/leek.png"));
        Image imgSegundos = iconoSegundos.getImage();
        gSegundos.drawImage(imgSegundos,0,0,960,720,null);
        gSegundos.dispose();

        //Asignacion de los buffers
        rotatedMinutos = puerroMinutos;
        rotatedHoras = puerroHoras;
        rotatedSegundos = puerroSegundos;
        //Segundero del reloj
        initComponents();
        Thread hilo = new Thread(this);
        hilo.start();


    }


    private void initComponents() {
        this.setLayout(null);
        JLabel lblTitulo = new JLabel("RELOJ ANALOGICO");
        lblTitulo.setBounds(420, 0, 200, 50);
        lblTitulo.setFont(new Font("arial", Font.PLAIN, 20));
        this.add(lblTitulo);


//        ImageIcon iconoHoras = new ImageIcon(getClass().getResource("img/hatsune_miku_leek.png"));
//        JLabel imgHoras = new JLabel(iconoHoras);
//        imgHoras.setBounds(290, 300, 350, 200);
//        this.add(imgHoras);
//
//        this.setComponentZOrder(imgHoras, 0);

//        ImageIcon iconoMinutos = new ImageIcon(getClass().getResource("img/hatsune_miku_leek_minutos.png"));
//        JLabel imgMinutos = new JLabel(iconoMinutos);
//        imgMinutos.setBounds(155, 267, 550, 207);
//        this.add(imgMinutos);
//        this.setComponentZOrder(imgMinutos, 0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(fondo, 0, 0, null);
        update(g);
    }

    @Override
    public void update(Graphics g) {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.SECOND) != sec) {
            hora = cal.get(Calendar.HOUR);
            min = cal.get(Calendar.MINUTE);
            sec = cal.get(Calendar.SECOND);

            rotatedMinutos = rotateImage(puerroMinutos, sec );
            rotatedHoras = rotateImage(puerroHoras, min);
            //redibujar el fondo y los puerros de la manera que se necesiten dependiendo de la hora
            //y el minuto en el que se encuentra el reloj de mi pc

        }
        g.drawImage(fondo, 0, 0, null);




        g.setClip(0, 0, getWidth(), getHeight());


        //pintar el buffer


        //pintar el ente movil
        g.drawImage(rotatedSegundos, 10, 80, null);
        g.drawImage(rotatedMinutos, 255, 267, null);
        g.drawImage(rotatedHoras, 350, 335, null);
    }

    @Override
    public void run() {
        while (true) {
            Graphics2D g2 = (Graphics2D) puerroHoras.getGraphics();
            //Graphics2D g4 = (Graphics2D) fondo.getGraphics();
            //Graphics2D g3 = (Graphics2D) puerroMinutos.getGraphics();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();
        }
    }

    private BufferedImage rotateImage (BufferedImage img, double angulo){
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width,height,img.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(Math.toRadians(angulo),width/2,height/2);
        g2d.drawImage(img,null,0,0);
        g2d.dispose();
        return rotatedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reloj Proyecto");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Ventana());
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
