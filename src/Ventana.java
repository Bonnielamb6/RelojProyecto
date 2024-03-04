import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Calendar;

public class Ventana extends JPanel implements Runnable {
//    private final BufferedImage puerroSegundos;
    private BufferedImage puerroMinutos;
    private BufferedImage puerroHoras;
    private BufferedImage fondo;
    private int hora;
    private int min;
    private int sec;


    public Ventana() {
        Dimension dimension = new Dimension(1000, 1000);
        setPreferredSize(dimension);


        //Fondo del reloj
        fondo = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics gfondo = fondo.getGraphics();
        ImageIcon iconoRelojFondo = new ImageIcon(getClass().getResource("img/clock_hatsune_miku.jpg"));
        Image img = iconoRelojFondo.getImage();
        gfondo.drawImage(img, 100, 60, 800, 800, null);
        gfondo.dispose();

        //Minutero del reloj
        puerroMinutos = new BufferedImage(550, 207, BufferedImage.TYPE_INT_ARGB);
        Graphics gMinutos = puerroMinutos.getGraphics();
        ImageIcon iconoMinutos = new ImageIcon(getClass().getResource("img/hatsune_miku_leek_minutos.png"));
        Image imgMinutos = iconoMinutos.getImage();
        gMinutos.drawImage(imgMinutos, 0, 0, 550, 207, null);
        gMinutos.dispose();
        if(imgMinutos ==null){
            System.out.println("null");
        }

        //Horario del reloj
        puerroHoras = new BufferedImage(350, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics gHoras = puerroHoras.getGraphics();
        ImageIcon iconoHoras = new ImageIcon(getClass().getResource("img/hatsune_miku_leek.png"));
        Image imgHoras = iconoHoras.getImage();
        if(imgHoras ==null){
            System.out.println("null2");
        }
        gHoras.drawImage(imgHoras, 0, 0, 350, 200, null);
        gHoras.dispose();

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
        g.drawImage(puerroMinutos, 155, 267, null);
        g.drawImage(puerroHoras, 290, 300, null);

    }

    @Override
    public void update(Graphics g) {
        paint(g);

        //g.setClip(0, 0, getWidth(), getHeight());
        //Calendar cal = Calendar.getInstance();
        //if (cal.get(Calendar.MINUTE) != min) {
        //    hora = cal.get(Calendar.HOUR);
        //    min = cal.get(Calendar.MINUTE);

        //redibujar el fondo y los puerros de la manera que se necesiten dependiendo de la hora
        //y el minuto en el que se encuentra el reloj de mi pc

        //}
        //pintar el buffer


        //pintar el ente movil
    }

    @Override
    public void run() {
        Graphics2D g2 = (Graphics2D) puerroHoras.getGraphics();
        Graphics2D g4 = (Graphics2D) fondo.getGraphics();
        Graphics2D g3 = (Graphics2D) puerroMinutos.getGraphics();

        while (true) {



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();
        }
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
