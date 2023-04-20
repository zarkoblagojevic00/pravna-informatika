import getFetchProxy from "./fetch-proxy";

const proxy = getFetchProxy();

export const getReasoningResult = (data) =>
  proxy.executeRequest({ path: `start_reasoning`, method: "POST", data });

export const createJudgement = (data) =>
  proxy.executeRequest({ path: `cases/add`, method: "POST", data });
