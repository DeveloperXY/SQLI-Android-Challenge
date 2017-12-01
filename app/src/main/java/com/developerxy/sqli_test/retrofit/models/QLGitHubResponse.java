package com.developerxy.sqli_test.retrofit.models;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This model represents the response returned by the Github GraphQL API.</p>
 * <quote>
 * This is an example of a GraphQL query, used in the context of this app :
 * {
 *  viewer {
 *      username: login
 *      repositories(first: 30, after: "Y3Vyc29yOnYyOpHOBHJa/w==") {
 *          pageInfo {
 *              hasNextPage
 *              endCursor
 *          }
 *          items: edges {
 *              repository: node {
 *                 name
 *                 url
 *                 createdAt
 *                 description
 *                 license
 *                 primaryLanguage {
 *                    name
 *                 }
 *                 isPrivate
 *              }
 *           }
 *      }
 *  }
 * }
 * </quote>
 *
 * <p>
 *     The GraphQL API will be queried using an HTTP client provided by Retrofit.
 *     Retrofit can be configured to parse the incoming JSON response automatically using Gson internally.
 *     But in order for it to do that, a class hierarchy must be setup according to the response JSON.
 *     <p>Here is a schema showing the class hierarchy used:</p>
 *
 *     QLGitHubResponse {
 *         QLGitHubData {
 *             QLGitHubViewer {
 *                 username
 *                 QLGitHubRepositories {
 *                      QLPageInfo
 *                      QLGitHubItems (list) [
 *                          QLGitHubItem {
 *                              QLGitHubRepository {
 *                                  ...
 *                              }
 *                          }
 *                      ]
 *                 }
 *             }
 *         }
 *     }
 * </p>
 */

public class QLGitHubResponse {
    private QLGitHubData data;

    public QLGitHubData getData() {
        return data;
    }

    /**
     * @return the list of repositories contained deep within the response hierarchy.
     */
    public List<QLGitHubRepository> extractRepositories() {
        List<QLGitHubRepository> repositories = new ArrayList<>();
        List<QLGitHubItem> repositoryItems = data.getViewer().getRepositories().getItems();

        for (QLGitHubItem item : repositoryItems) {
            repositories.add(item.getRepository());
        }

        return repositories;
    }

    /**
     * @return pagination information about the current response data.
     */
    public QLPageInfo getPaginationInfo() {
        return data.getViewer()
                .getRepositories()
                .getPageInfo();
    }
}
