package ma.ensa.projetws.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import ma.ensa.projetws.R;
import ma.ensa.projetws.beans.Etudiant;

public class ListAdapter extends BaseAdapter {
    private List<Etudiant> etudiants;
    private LayoutInflater inflater;

    public ListAdapter(List<Etudiant> etudiants, Activity activity) {
        this.etudiants = etudiants;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return etudiants.size();
    }

    @Override
    public Object getItem(int position) {
        return etudiants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return etudiants.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_student, null);

        TextView id = convertView.findViewById(R.id.id);
        TextView nom = convertView.findViewById(R.id.nom);
        TextView prenom = convertView.findViewById(R.id.prenom);
        TextView ville = convertView.findViewById(R.id.ville);
        TextView sexe = convertView.findViewById(R.id.sexe);

        Etudiant etudiant = etudiants.get(position);

        id.setText(String.valueOf(etudiant.getId()));
        nom.setText(etudiant.getNom());
        prenom.setText(etudiant.getPrenom());
        ville.setText(etudiant.getVille());
        sexe.setText(etudiant.getSexe());

        return convertView;
    }
}
