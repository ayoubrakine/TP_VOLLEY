package ma.ensa.projetws;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.ensa.projetws.adapter.ListAdapter;
import ma.ensa.projetws.beans.Etudiant;

public class List_Students extends AppCompatActivity {

    private List<Etudiant> etudiants;
    private ListView listView;
    private RequestQueue requestQueue;
    private String loadUrl = "http://192.168.1.16/PhpProject1/ws/loadEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_students);

        listView = findViewById(R.id.list);
        etudiants = new ArrayList<>(); // Initialisation de la liste

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, loadUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject etudiantJson = response.getJSONObject(i);
                                Etudiant etudiant = new Etudiant();
                                etudiant.setId(etudiantJson.getInt("id"));
                                etudiant.setNom(etudiantJson.getString("nom"));
                                etudiant.setPrenom(etudiantJson.getString("prenom"));
                                etudiant.setVille(etudiantJson.getString("ville"));
                                etudiant.setSexe(etudiantJson.getString("sexe"));
                                etudiants.add(etudiant);
                            }

                            ListAdapter studentadapter = new ListAdapter(etudiants, List_Students.this);
                            listView.setAdapter(studentadapter);
                            studentadapter.notifyDataSetChanged();

                            // Logs de débogage
                            Log.d("List_Students", "Données chargées avec succès. Nombre d'étudiants : " + etudiants.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Logs de débogage en cas d'erreur
                            Log.e("List_Students", "Erreur lors de la récupération des données JSON : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(List_Students.this, "Erreur de chargement des étudiants", Toast.LENGTH_SHORT).show();
                        // Logs de débogage en cas d'erreur
                        Log.e("List_Students", "Erreur de réseau : " + error.getMessage());
                    }
                });

        requestQueue.add(request);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Etudiant etudiant = etudiants.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(List_Students.this);
                builder.setTitle("Options pour l'étudiant");
                builder.setMessage("Que voulez-vous faire avec cet étudiant ?");

                // Bouton "Modifier"
                builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Créez une boîte de dialogue personnalisée pour la modification
                        AlertDialog.Builder modifyBuilder = new AlertDialog.Builder(List_Students.this);
                        modifyBuilder.setTitle("Modification");

                        // Inclure la mise en page personnalisée contenant les champs de texte
                        View dialogView = getLayoutInflater().inflate(R.layout.update_student, null);
                        modifyBuilder.setView(dialogView);

                        // Récupérez les champs de texte de la mise en page personnalisée
                        TextView idTextView = dialogView.findViewById(R.id.idstudent);
                        EditText nomEditText = dialogView.findViewById(R.id.nom);
                        EditText prenomEditText = dialogView.findViewById(R.id.prenom);
                        EditText villeEditText = dialogView.findViewById(R.id.ville);
                        EditText sexeEditText = dialogView.findViewById(R.id.sexe);

                        // Préremplissez les champs de texte avec les valeurs actuelles de l'étudiant
                        idTextView.setText(String.valueOf(etudiant.getId()));
                        nomEditText.setText(etudiant.getNom());
                        prenomEditText.setText(etudiant.getPrenom());
                        villeEditText.setText(etudiant.getVille());
                        sexeEditText.setText(etudiant.getSexe());

                        modifyBuilder.setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Récupérez les nouvelles valeurs des champs de texte
                                String newNom = nomEditText.getText().toString();
                                String newPrenom = prenomEditText.getText().toString();
                                String newVille = villeEditText.getText().toString();
                                String newSexe = sexeEditText.getText().toString();

                                String iddd = idTextView.getText().toString();
                                int idd = Integer.parseInt(iddd);

                                // Construisez l'URL pour la mise à jour de l'étudiant
                                String updateUrl = "http://192.168.1.16/PhpProject1/ws/updateEtudiant.php?id=" + idd +
                                        "&nom=" + newNom + "&prenom=" + newPrenom + "&ville=" + newVille + "&sexe=" + newSexe;

                                // Envoyez une demande POST pour mettre à jour l'étudiant
                                StringRequest updateRequest = new StringRequest(
                                        Request.Method.POST,
                                        updateUrl,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Gérez ici la réponse de la mise à jour, si nécessaire

                                                // Mettez à jour l'objet 'etudiant' localement avec les nouvelles valeurs
                                                etudiant.setNom(newNom);
                                                etudiant.setPrenom(newPrenom);
                                                etudiant.setVille(newVille);
                                                etudiant.setSexe(newSexe);

                                                // Mettez à jour l'adaptateur de la liste pour refléter les modifications
                                                ListAdapter studentadapter = new ListAdapter(etudiants, List_Students.this);
                                                listView.setAdapter(studentadapter);
                                                studentadapter.notifyDataSetChanged();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // Gérez ici les erreurs de la mise à jour, si nécessaire
                                            }
                                        }
                                );

                                // Ajoutez la demande de mise à jour à la file d'attente de Volley
                                requestQueue.add(updateRequest);
                                dialog.dismiss();
                            }
                        });

                        modifyBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        modifyBuilder.show();
                    }
                });



                // Bouton "Supprimer"
                builder.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Afficher un autre AlertDialog pour la confirmation de suppression
                        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(List_Students.this);
                        confirmBuilder.setTitle("Confirmation de suppression");
                        confirmBuilder.setMessage("Voulez-vous vraiment supprimer cet étudiant ?");

                        confirmBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int etudiantId = etudiant.getId();
                                String deleteUrl = "http://192.168.1.16/PhpProject1/ws/deleteEtudiant.php?id=" + etudiantId;

                                //demande POST pour supprimer l'étudiant
                                StringRequest deleteRequest = new StringRequest(
                                        Request.Method.POST,
                                        deleteUrl,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Suppression réussie, supprimez également de la liste locale
                                                etudiants.remove(etudiant);

                                                // Mettez à jour l'adaptateur de la liste

                                                ListAdapter studentadapter = new ListAdapter(etudiants, List_Students.this);
                                                listView.setAdapter(studentadapter);
                                                studentadapter.notifyDataSetChanged();

                                                Toast.makeText(List_Students.this, "Étudiant supprimé avec succès", Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(List_Students.this, "Erreur lors de la suppression de l'étudiant", Toast.LENGTH_SHORT).show();
                                                Log.e("List_Students", "Erreur de réseau lors de la suppression : " + error.getMessage());
                                            }
                                        }
                                );

                                // Ajouter la demande de suppression à la file d'attente de Volley
                                requestQueue.add(deleteRequest);
                            }
                        });

                        confirmBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        confirmBuilder.show();
                    }
                });

                builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
}
