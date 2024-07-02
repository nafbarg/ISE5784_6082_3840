package primitives;

public class Material {
    public Double3 kD  = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public Double3 kT = Double3.ZERO;
    public Double3 kR = Double3.ZERO;
    public int nShininess = 0;


    /**
     * setter for kD in Phong model
     *
     * @param kD diffuse attenuation factor
     * @return the material itself
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter for kD in phong model
     *
     * @param kD diffuse attenuation factor
     * @return the material itself
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * setter for kS in Phong model
     *
     * @param kS specular attenuation factor
     * @return the material itself
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter for kS in phong model
     *
     * @param kS specular attenuation factor
     * @return the material itself
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setter for nShininess in phong model
     *
     * @param ns shininess factor
     * @return the material itself
     */
    public Material setShininess(int ns) {
        nShininess = ns;
        return this;
    }

    /**
     * setter for kT in transparency model
     *
     * @param kT refraction attenuation factor
     * @return the material itself
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter for kT in transparency model
     *
     * @param kT refraction attenuation factor
     * @return the material itself
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setter for kR in reflection model
     *
     * @param kR reflection attenuation factor
     * @return the material itself
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter for kR in reflection model
     *
     * @param kR reflection attenuation factor
     * @return the material itself
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}