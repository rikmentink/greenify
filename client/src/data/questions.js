const questions = [
    {
        question: "facilitatingFactor",
        options: [
            {
                name: "TOTALLY_DISAGREE",
                displayName: "Helemaal oneens",
                value: 0
            },
            {
                name: "DISAGREE",
                displayName: "Oneens",
                value: 1
            },
            {
                name: "NEUTRAL",
                displayName: "Neutraal",
                value: 2
            },
            {
                name: "AGREE",
                displayName: "Eens",
                value: 3
            },
            {
                name: "TOTALLY_AGREE",
                displayName: "Helemaal eens",
                value: 4
            },
            // {
            //     name: "I_DONT_KNOW",
            //     displayName: "Don't know",
            //     value: 0
            // }
        ]
    },
    {
        question: "priority",
        options: [
            {
                name: "NO_PRIORITY",
                displayName: "Geen prioriteit",
                value: 3
            },
            {
                name: "LITTLE_PRIORITY",
                displayName: "Weinig prioriteit",
                value: 2
            },
            {
                name: "PRIORITY",
                displayName: "Prioriteit",
                value: 1
            },
            {
                name: "TOP_PRIORITY",
                displayName: "Hoogste prioriteit",
                value: 0
            }
        ]
    }
];

export default { questions };