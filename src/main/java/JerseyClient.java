import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import dao.UserDAO;
import dto.UserDTO;
import model.Comment;
import model.Post;
import model.User;

public class JerseyClient {
    public static void main(String[] args) {
        try {
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(
                    JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);

            System.out.println("Resultado da API");
            for (int x = 1; x <= 10; x++){
                UserDAO userDAO = new UserDAO();
                UserDTO userDTOs = new UserDTO();
                User user = new User();
                WebResource webResource = client
                        .resource("https://jsonplaceholder.typicode.com/users/" + x);
                ClientResponse response = webResource.accept("application/json")
                        .type("application/json").get(ClientResponse.class);
                User userResponse = response.getEntity(User.class);

                System.out.println("salvando no user");
                user.setId(userResponse.getId());
                user.setName(userResponse.getName());
                user.setUsername(userResponse.getUsername());
                user.setEmail(userResponse.getEmail());
                user.setAddress(userResponse.getAddress());
                user.setCompany(userResponse.getCompany());
                user.setPhone(userResponse.getPhone());
                user.setWebsite(userResponse.getWebsite());

                System.out.println("salvando no dto");
                userDTOs.setId(user.getId());
                userDTOs.setName(user.getName());
                userDTOs.setEmail(user.getEmail());
                userDTOs.setUsername(user.getUsername());

                System.out.println("salvando no db");
                userDAO.save(userDTOs);
            }



//            System.out.println("Comentários");
//            for (int x = 1; x <= 10; x++) {
//                WebResource webResource = client
//                        .resource("https://jsonplaceholder.typicode.com/comments/" + x);
//                ClientResponse response = webResource.accept("application/json")
//                        .type("application/json").get(ClientResponse.class);
//                Comment userResponse = response.getEntity(Comment.class);
//                System.out.println(userResponse.toString());
//            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}