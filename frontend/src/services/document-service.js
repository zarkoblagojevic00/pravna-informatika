import getFetchProxy from "./fetch-proxy";

const proxy = getFetchProxy();

export const getJudgementNames = () =>
  proxy.executeRequest({ path: `documents/judgement` });

export const getJudgementHTML = (judgementId) =>
  proxy.fetchHTML({ path: `documents/judgement/html/${judgementId}` });

export const getLawHTML = () => proxy.fetchHTML({ path: `documents/law/html` });

export const getJudgementFeatures = () =>
  proxy
    .fetchHTML({ path: `documents/features` })
    .then((features) => JSON.parse(features));
