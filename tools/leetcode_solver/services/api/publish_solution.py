import json
import logging
from typing import Optional, Dict
from services.api.base import LeetCodeAPIBaseService


class PublishSolutionAPIService(LeetCodeAPIBaseService):
    """Service to publish a solution article on LeetCode via GraphQL mutation."""
    def publish_solution(
        self,
        title: str,
        content: str,
        tags: list,
        question_slug: str,
        summary: str = "",
        thumbnail: str = "",
        is_serialized: bool = False,
    ) -> Optional[Dict]:
        """Publish a solution article and return the response data if successful."""
        url = f"{self.config.BASE_URL}/graphql"
        headers = self.config.DEFAULT_HEADERS.copy()
        headers["Referer"] = (
            f"{self.config.BASE_URL}/problems/{question_slug}/solution/"
        )

        query = """
        mutation ugcArticlePublishSolution($data: UgcArticlePublishSolutionInput!) {
            ugcArticlePublishSolution(data: $data) {
                ok
                error
                article {
                    ...ugcSolutionArticleFragment
                    content
                    isAuthorArticleReviewer
                    hasVideoArticle
                    isSerialized
                    scoreInfo {
                        scoreCoefficient
                    }
                    prev {
                        uuid
                        slug
                        topicId
                        title
                    }
                    next {
                        uuid
                        slug
                        topicId
                        title
                    }
                }
            }
        }

        fragment ugcSolutionArticleFragment on SolutionArticleNode {
            uuid
            title
            slug
            summary
            author {
                realName
                userAvatar
                userSlug
                userName
                nameColor
                certificationLevel
                activeBadge {
                    icon
                    displayName
                }
            }
            articleType
            thumbnail
            summary
            createdAt
            updatedAt
            status
            isLeetcode
            canSee
            canEdit
            isMyFavorite
            chargeType
            myReactionType
            topicId
            hitCount
            hasVideoArticle
            reactions {
                count
                reactionType
            }
            title
            slug
            tags {
                name
                slug
                tagType
            }
            topic {
                id
                topLevelCommentCount
            }
        }
        """

        variables = {
            "data": {
                "title": title,
                "content": content,
                "tags": tags,
                "questionSlug": question_slug,
                "summary": summary,
                "thumbnail": thumbnail,
                "isSerialized": is_serialized,
            }
        }

        payload = {
            "query": query,
            "variables": variables,
            "operationName": "ugcArticlePublishSolution",
        }

        response = self.session.post(url, headers=headers, json=payload)

        if response.status_code != 200:
            logging.error(
                f"Failed to publish solution for {question_slug}: HTTP {response.status_code}"
            )
            return None

        try:
            data = response.json()
            result = data.get("data", {}).get("ugcArticlePublishSolution", {})
            if result.get("ok"):
                logging.info(f"Successfully published solution for {question_slug}")
                return result.get("article")
            else:
                logging.error(
                    f"Failed to publish solution for {question_slug}: {result.get('error')}"
                )
                return None
        except json.JSONDecodeError as e:
            logging.error(f"Error parsing publish solution response: {e}")
            return None
