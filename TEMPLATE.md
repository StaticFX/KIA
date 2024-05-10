# KIA - Kotlin Inventory API

[![Dokka](https://img.shields.io/badge/JavaDoc-Online-green)](https://staticfx.github.io/KIA/)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/staticfx/kia/workflow.yml)
![GitHub Release](https://img.shields.io/github/v/release/staticfx/kia)

```js
module.exports = {
    LATEST_VIDL_RELEASE: {
        type: 'customQuery',
        loop: false,
        query: async (octokit, moment, user) => {
            // You can do anything  you want with the GitHub API here.
            const result = await octokit.graphql(`
        query {
          repository(name: "vidl", owner: "${user.USERNAME}") {
            releases(last: 1) {
              edges {
                node {
                  url
                  publishedAt
                  tagName
                }
              }
            }
          }
        }
      `)
            const release = result.repository.releases.edges[0].node
            const date = new Date(release.publishedAt)
            // We have `loop: false`, so we return an object.
            // If we had `loop: true`, we would return an array of objects.
            return {
                VIDL_RELEASE_TAG: release.tagName,
                VIDL_RELEASE_URL: release.url,
                VIDL_RELEASE_WHEN: moment(release.publishedAt).fromNow(),
            }
        }
    }
}
```


Maven:
```
<dependency>
  <groupId>de.staticred.kia</groupId>
  <artifactId>kia</artifactId>
  <version> {{ LATEST_VIDL_RELEASE }} </version>
</dependency>
```



KIA is an API programed using the papermc api to make inventory management and its creation easier.
KIA allows to set event listeners directly on the Inventory Items instead of manually listening to them.
In order to keep track of the Items KIA uses the NBT data of an item to set a UUID and match it with the corresponding item.

## Other Features
- NBT System for Inventory Matching (Gets rid of inventory title issues)
- Supports inventory rows
- Inventory animations
