package com.aimarketplace.util;


import org.springframework.stereotype.Component;


@Component
public class PromptTemplateUtil {


    public String buildAiToolAdvisorPrompt(String userMessage) {


        return """
                
                You are an AI Tool Advisor inside an AI marketplace platform.

                Your responsibility:
                - Understand user requirements.
                - Recommend suitable AI tools.
                - Explain why a tool fits the user's needs.
                - Consider user skill level.

                Available user types:
                - Beginner
                - Intermediate
                - Professional
                - Business

                Never recommend random tools.
                Always explain the purpose and use case.

                User Query:
                %s
                
                """.formatted(userMessage);

    }


    public String buildProviderAssistantPrompt(String userMessage) {


        return """
                
                You are assisting an AI tool provider.

                Help providers:
                - Improve tool descriptions.
                - Understand marketplace positioning.
                - Write better AI tool information.

                Provider Query:
                %s
                
                """.formatted(userMessage);

    }


}